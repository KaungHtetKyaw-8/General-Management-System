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

// ***************************
// select change and target value change
// ***************************
const selectors = document.querySelectorAll(".selector");

selectors.forEach(selector =>  {
    selector.addEventListener('change', evt => {
        const selectedOption = evt.target.selectedOptions[0];
        const changeValue = selectedOption.getAttribute('data-change-value');

        const targetSelector = selector.getAttribute('data-target');
        const target = document.querySelector(`${targetSelector}`);

        if (target) {
            target.value = changeValue;
        }
    });
});



// ***************************
// Input Element Prevent Submit
// ***************************
const inputs = document.querySelectorAll("#multiForm input")
inputs.forEach(input => {
    input.addEventListener("keydown", evt => {
        if (evt.key === "Enter"){
            evt.preventDefault()
        }
    });
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
        const filtered = countryList.filter(item => item.toLowerCase().startsWith(query.toLowerCase())).slice(0,5);
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
        const filtered = cityList[0].filter(item => item.toLowerCase().startsWith(query.toLowerCase())).slice(0,5);
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
        const rendererKey = input.dataset.renderer;
        const fetcherKey = input.dataset.fetcher;
        const fetcherConfig = input.dataset.fetcherconfig || "no-repeat";

        const customRenderer = rendererMap[rendererKey] || defaultRenderer;
        const customFetcher = fetcherMap[fetcherKey] || defaultFetcher;

        input.addEventListener('input', () => {
            clearTimeout(input.debounceTimeout);
            input.debounceTimeout = setTimeout(() => {

                input.value = input.value.replace(/[^a-zA-Z0-9]/g, '');
                const query = input.value.trim().toLowerCase();
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
                        });
                }
            }, 300);
        });
    });
}

setupSuggestionInput('suggest-input', rendererMap, fetcherMap);


// ***************************
// Select and target Option List Create
// ***************************
function setupSelector(className, fetcherMap) {
    const selectors = document.querySelectorAll(`.${className}`);

    selectors.forEach(selector =>{
        const target = document.querySelector('${selector.dataset.target}')
        const url = selector.dataset.url;
        const fetcherKey = selector.dataset.fetcher;

        const customRenderer = rendererMap[rendererKey] || defaultRenderer;
        const customFetcher = fetcherMap[fetcherKey] || defaultFetcher;

        selector.addEventListener('change', function () {
            const selectedOption = this.options[this.selectedIndex];
            const selectValue = selectedOption.getAttribute('data-value');
            const target = this.getAttribute('data-target');
            target.value = selectValue;
        });
    })
}
setupSelector('multi-selector', fetcherMap)


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

    if ((input.nextElementSibling)&&(input.nextElementSibling.className.includes('list-group'))){
        input.nextElementSibling.remove();
    }

    const div = document.createElement("div");
    div.className = 'list-group position-absolute shadow'
    div.style.display = 'none'
    div.style.zIndex = '5';

    data.forEach(item => {
        const btn = document.createElement('button');
        btn.className = 'list-group-item list-group-item-action btn-light text-left w-100';
        btn.innerHTML = `${item}`;
        btn.type = 'button'
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
const chartOptionMap = {
    donutAndPieOption : {
        maintainAspectRatio: false,
        plugins: {
            title: {
                display: true,
                text: 'Custom Chart Title',
                padding: {
                    top: 10,
                    bottom: 20
                }
            }
        }
    },
    barOption : {
        maintainAspectRatio: false,
        plugins: {
            title: {
                display: true,
                text: 'Custom Chart Title',
                padding: {
                    top: 10,
                    bottom: 20
                }
            }
        },
        scales: {
            y: {
                // stacked: true,
                // beginAtZero: true,
                grid: {
                    display: true,
                    color: "rgba(255,99,132,0.2)"
                }
            },
            x: {

                grid: {
                    display: false
                }
            }
        }
    }

};

function getOptionKey(chartType) {
    if (chartType === 'doughnut' || chartType === 'pie') return 'donutAndPieOption';
    if (chartType === 'bar') return 'barOption';
    return null;
}


function createNewChart(idName,fetcher,option){
    const targetElement = document.querySelector(idName);

    if (!targetElement){
        console.error("Chart target element not found.")
        return;
    }

    const url = targetElement.dataset.url;
    const chartName = targetElement.dataset.name;
    const chartType = targetElement.dataset.charttype;


    const customFetcher = fetcher?.[chartName] ?? fetcher['noParmFetcher'];

    let targetOption = JSON.parse(JSON.stringify(option[getOptionKey(chartType)]));

    targetOption.plugins.title.text = chartName;

    // fetch(`${url}`)
    //     .then(res => res.json())
    customFetcher(url)
        .then(data => {
            const config = {
                type: chartType,
                options: targetOption,
                data: data
            };
            new Chart(targetElement.firstElementChild, config);
        })
        .catch(err => {
            console.error('Chart fetch error:', err);
        });
};

createNewChart('#char1', fetcherMap, chartOptionMap)
createNewChart('#char2', fetcherMap, chartOptionMap)
createNewChart('#char3', fetcherMap, chartOptionMap)
createNewChart('#char4', fetcherMap, chartOptionMap)