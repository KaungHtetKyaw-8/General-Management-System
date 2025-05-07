// ***************************
// Form Slider
// ***************************
const steps = document.querySelectorAll(".form-step");
const nextBtns = document.querySelectorAll(".form-next-btn");
const prevBtns = document.querySelectorAll(".form-prev-btn");
let currentStep = 0;

nextBtns.forEach(btn => {
    btn.addEventListener("click", () => {
        if (currentStep < steps.length - 1) {
            steps[currentStep].classList.remove("active");
            currentStep++;
            steps[currentStep].classList.add("active");
        }
    });
});

prevBtns.forEach(btn => {
    btn.addEventListener("click", () => {
        if (currentStep > 0) {
            steps[currentStep].classList.remove("active");
            currentStep--;
            steps[currentStep].classList.add("active");
        }
    });
});

// document.getElementById("multiStepForm").addEventListener("submit", (e) => {
//     e.preventDefault();
//     alert("Form submitted!");
// });



// ***************************
// Check List
// ***************************

function defaultFetcher(url, query) {
    return fetch(`${url}?q=${encodeURIComponent(query)}`).then(res => res.json());
}

// 🔹 Default simple renderer
function defaultRenderer(data, input, box) {
    data.forEach(item => {
        const li = document.createElement('li');
        li.className = 'list-group-item list-group-item-action';
        li.textContent = item;
        li.onclick = () => {
            input.value = item;
            box.style.display = 'none';
        };
        box.appendChild(li);
    });
}

// Fetch Mapper
const fetcherMap = {
    countryAndCityFetcher: (url, query) => {
        return fetch(`${url}`).then(res => res.json());
    },
    XXXFetcher: (url, query) => {
        return fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ search: query })
        }).then(res => res.json());
    }
};

// Render Mapper
const rendererMap = {
    countryRenderer: (data, query, input,box) => {
        const countryList = extractKeyToList(data,"country");
        const filtered = countryList.filter(item => item.toLowerCase().includes(query.toLowerCase())).slice(0,5);
        if (filtered.length === 0) {
            box.style.display = 'none';
            return;
        }
        filtered.forEach(item => {
            const li = document.createElement('li');
            li.className = 'list-group-item list-group-item-action d-flex justify-content-between';
            li.innerHTML = `${item}`;
            li.onclick = () => {
                input.value = item;
                box.style.display = 'none';
            };
            box.appendChild(li);
        });
    },
    cityRenderer: (data, query, input, box) => {

        const countryName = document.getElementById("country").value;

        if (countryName === null){
            console.error("Country Not Found [ID : country]",countryName)
            return;
        }
        const cityList = extractMapKeyToMapList(data,countryName,"country","cities");

        console.log(cityList[0]);

        const filtered = cityList[0].filter(item => item.toLowerCase().includes(query.toLowerCase())).slice(0,5);
        if (filtered.length === 0) {
            box.style.display = 'none';
            return;
        }
        filtered.forEach(item => {
            const li = document.createElement('li');
            li.className = 'list-group-item list-group-item-action d-flex justify-content-between';
            li.innerHTML = `${item}`;
            li.onclick = () => {
                input.value = item;
                box.style.display = 'none';
            };
            box.appendChild(li);
        });
    },
    productRenderer: (data, query, input, box) => {
        data.forEach(product => {
            const li = document.createElement('li');
            li.className = 'list-group-item list-group-item-action d-flex justify-content-between';
            li.innerHTML = `<span>${product.name}</span><small class="text-muted">$${product.price}</small>`;
            li.onclick = () => {
                input.value = product.name;
                box.style.display = 'none';
            };
            box.appendChild(li);
        });
    }
};

function setupSuggestionInput(className, rendererMap, fetcherMap) {
    const inputs = document.querySelectorAll(`.${className}`);
    const fetchCache = {}; // Cache by URL

    inputs.forEach(input => {
        const url = input.dataset.url;
        const suggestBox = document.querySelector(input.dataset.target);
        const rendererKey = input.dataset.renderer;
        const fetcherKey = input.dataset.fetcher;
        const fetcherConfig = input.dataset.fetcherConfig || "no-repeat";

        const customRenderer = rendererMap[rendererKey] || defaultRenderer;
        const customFetcher = fetcherMap[fetcherKey] || defaultFetcher;

        input.addEventListener('input', () => {
            const query = input.value.trim().toLowerCase();
            suggestBox.innerHTML = '';
            if (!query) {
                suggestBox.style.display = 'none';
                return;
            }

            const useData = data => {
                customRenderer(data, query, input, suggestBox);
                suggestBox.style.display = 'block';
            };

            if (fetchCache[url] && fetcherConfig !== "repeat") {
                useData(fetchCache[url]);
            } else {
                customFetcher(url)
                    .then(data => {
                        fetchCache[url] = data["data"];
                        useData(fetchCache[url]);
                    })
                    .catch(err => {
                        console.error('Suggestion fetch error:', err);
                        suggestBox.style.display = 'none';
                    });
            }
        });

        document.addEventListener('click', e => {
            if (!input.contains(e.target) && !suggestBox.contains(e.target)) {
                suggestBox.style.display = 'none';
            }
        });
    });
}

setupSuggestionInput('suggest-input', rendererMap, fetcherMap);


// ***************************
// Utils
// ***************************
function extractKeyToList(data, key) {
    if (!Array.isArray(data)) {
        console.error('Expected an array but got:', data);
        return [];
    }

    return data
        .filter(item => item && typeof item === 'object' && key in item)
        .map(item => item[key]);
}

function extractMapKeyToMapList(dataArray, mapValue, mapKey, getKey) {
    if (!Array.isArray(dataArray)) {
        console.error('Expected an array but got:', dataArray);
        return [];
    }

    return dataArray
        .filter(item => item && typeof item === 'object' && mapKey in item && item[mapKey] === mapValue)
        .map(item => item[getKey]);
}






// ***************************
// Charts
// ***************************
const ctx = document.getElementById('myChart');

const donut_data = {
    labels: [
        'Red',
        'Blue',
        'Yellow'
    ],
    datasets: [{
        label: 'My First Dataset',
        data: [300, 50, 100],
        backgroundColor: [
            'rgb(255, 99, 132)',
            'rgb(54, 162, 235)',
            'rgb(255, 205, 86)'
        ],
        hoverBackgroundColor: [
            '#E76D13',
            '#15E53C',
            '#DF1D98'
        ],
        borderAlign: 'inner',
        hoverOffset: 4
    }]
};

const config = {
    type: 'doughnut',
    data: donut_data,
};

const config1 = {
    type: 'bar',
    data: {
        labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
        datasets: [{
            label: '# of Votes',
            data: [12, 19, 3, 5, 2, 3],
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
};



new Chart(ctx, config);