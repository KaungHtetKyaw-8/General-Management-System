// ***************************
// Form Slider
// ***************************
const steps = document.querySelectorAll(".form-step");
const nextBtn = document.querySelectorAll(".form-next-btn");
const prevBtn = document.querySelectorAll(".form-prev-btn");
let currentStep = 0;

nextBtn.forEach(btn => {
    btn.addEventListener("click", () => {
        if (currentStep < steps.length - 1) {
            steps[currentStep].classList.remove("active");
            currentStep++;
            steps[currentStep].classList.add("active");
        }
    });
});

prevBtn.forEach(btn => {
    btn.addEventListener("click", () => {
        if (currentStep > 0) {
            steps[currentStep].classList.remove("active");
            currentStep--;
            steps[currentStep].classList.add("active");
        }
    });
});

document.getElementById("multiStepForm").addEventListener("submit", (e) => {
    e.preventDefault();
    alert("Form submitted!");
});



// ***************************
// Suggestion List
// ***************************

// 🔹 Default fetcher
function defaultFetcher(url, query) {
    return fetch(`${url}?q=${encodeURIComponent(query)}`).then(res => res.json());
}

// 🔹 Default simple renderer
function defaultRenderer(data, query, input) {
    if (data.length === 0){
        return;
    }
    suggestionListCreate(data,input);
}

// Fetch Mapper
const fetcherMap = {
    noParmFetcher: (url) => {
        return fetch(`${url}`).then(res => res.json());
    }
};

// Render Mapper
const rendererMap = {
    countryRenderer: (data, query, input) => {
        const countryList = extractKeyToList(data,"country");
        const filtered = countryList.filter(item => item.toLowerCase().includes(query.toLowerCase())).slice(0,5);
        if (filtered.length === 0) {
            return;
        }
        suggestionListCreate(filtered, input);
    },
    cityRenderer: (data, query, input) => {
        const countryName = document.getElementById("address.countryCode").value;
        if (countryName === null){
            console.error("Country Not Found [ID : country]",countryName)
            return;
        }
        const cityList = extractMapKeyToMapList(data,countryName,"country","cities");
        const filtered = cityList[0].filter(item => item.toLowerCase().includes(query.toLowerCase())).slice(0,5);
        if (filtered.length === 0) {
            return;
        }
        suggestionListCreate(filtered,input);
    },
    productRenderer: (data, query, input) => {
        suggestionListCreate(data,input);
    }
};

function setupSuggestionInput(className, rendererMap, fetcherMap) {
    const inputs = document.querySelectorAll(`.${className}`);
    const fetchCache = {}; // Cache by URL

    inputs.forEach(input => {
        const url = input.dataset.url;
        // const suggestBox = document.querySelector(input.dataset.target);
        const rendererKey = input.dataset.renderer;
        const fetcherKey = input.dataset.fetcher;
        const fetcherConfig = input.dataset.fetcherconfig || "no-repeat";

        const customRenderer = rendererMap[rendererKey] || defaultRenderer;
        const customFetcher = fetcherMap[fetcherKey] || defaultFetcher;

        input.addEventListener('input', () => {
            input.value = input.value.replace(/[^a-zA-Z1-9]/g, '');
            const query = input.value.trim().toLowerCase();
            // suggestBox.innerHTML = '';
            if (!query) {
                if (input.nextElementSibling.className.includes('list-group')){
                    input.nextElementSibling.remove();
                }
                return;
            }

            const useData = data => {
                customRenderer(data, query, input);
            };

            if (fetchCache[url] && fetcherConfig !== "repeat") {
                useData(fetchCache[url]);
            } else {
                customFetcher(url,query)
                    .then(data => {
                        fetchCache[url] = data["data"];
                        useData(fetchCache[url]);
                    })
                    .catch(err => {
                        console.error('Suggestion fetch error:', err);
                        // suggestBox.style.display = 'none';
                    });
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

function suggestionListCreate(data,input){

    if (input.nextElementSibling.className.includes('list-group')){
        input.nextElementSibling.remove();
    }

    const div = document.createElement("div");
    div.className = 'list-group position-absolute shadow'
    div.nodeType= 'button'
    div.style.display = 'none'

    data.forEach(item => {
        const btn = document.createElement('button');
        btn.className = 'list-group-item list-group-item-action btn-light';
        btn.innerHTML = `${item}`;
        btn.onclick = () => {
            input.value = item;
            div.style.display = 'none';
        };
        div.appendChild(btn);
    });
    input.parentNode.insertBefore(div,input.nextSibling);
    div.style.display = 'block'

    document.addEventListener('click', e => {
        if (!input.contains(e.target) && !div.contains(e.target)) {
            div.style.display = 'none';
        }
    });
}






// ***************************
// Charts
// ***************************
const ctx = document.getElementById('myChart');

const donut_data = {
    labels: ['Data1', 'Data2', 'Data3'],
    datasets: [{
        label: 'My First Dataset',
        data: [300, 50, 100],
        backgroundColor: [
            'rgb(99,255,213)',
            'rgb(54,96,235)',
            'rgb(255,86,86)'
        ],
        hoverOffset: 4
    }]
};

const config = {
    type: 'doughnut',
    data: donut_data,
};


const lineData = {
    labels: [
        "Jan",
        "Feb",
        "Mar",
        "Apr",
        "May",
        "Jun",
        "July",
        "Aug",
        "Sept",
        "Oct",
        "Nov",
        "Dec"
    ] ,
    datasets: [{
        label: 'My First Dataset',
        data: [65, 59, 80, 81, 56, 55, 40],
        backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(255, 159, 64, 0.2)',
            'rgba(255, 205, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(54, 162, 235, 0.2)',
            'rgba(153, 102, 255, 0.2)',
            'rgba(201, 203, 207, 0.2)'
        ],
        borderColor: [
            'rgb(255, 99, 132)',
            'rgb(255, 159, 64)',
            'rgb(255, 205, 86)',
            'rgb(75, 192, 192)',
            'rgb(54, 162, 235)',
            'rgb(153, 102, 255)',
            'rgb(201, 203, 207)'
        ],
        borderWidth: 1
    }]
};

const config1 = {
    type: 'bar',
    data: lineData,
    options: {
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
};



new Chart(ctx, config);