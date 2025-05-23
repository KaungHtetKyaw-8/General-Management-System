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
            input.value = input.value.replace(/[^a-zA-Z1-9]/g, '');
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

    if ((input.nextElementSibling)&&(input.nextElementSibling.className.includes('list-group'))){
        input.nextElementSibling.remove();
    }

    const div = document.createElement("div");
    div.className = 'list-group position-absolute shadow'
    div.style.display = 'none'

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
// Custom Collapse
// ***************************

// const collapseElementList = document.querySelectorAll('.custom-collapse');
// const collapseList = [...collapseElementList].map(collapseEl => new bootstrap.Collapse(collapseEl, {
//     toggle: false,
//     delay : {
//         show:10000,
//         hide:150
//     }
// }));
//
//
// const sideBar = document.getElementById("sidebar");
//
// if (sideBar){
//     sideBar.addEventListener("mouseenter", () => {
//         sideBar.style.width= '100%';
//         collapseList.forEach(value => {
//             value.show();
//         });
//     });
//
//     sideBar.addEventListener("mouseleave", () => {
//         collapseList.forEach(value => value.hide());
//         sideBar.style.width= '4.5rem';
//     });
// }



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

createNewChart('#employeeGender', fetcherMap, chartOptionMap)
createNewChart('#employeeAge', fetcherMap, chartOptionMap)
createNewChart('#employeeDepartment', fetcherMap, chartOptionMap)
createNewChart('#employeeCity', fetcherMap, chartOptionMap)


const colorList =  {
    def : [
        '#FFF1C9',
        '#F7B7A3',
        '#EA5F89',
        '#9B3192',
        '#57167E',
        '#2B0B3F'
    ],
    def_transparent : [
        '#FFF1C94C',
        '#F7B7A34C',
        '#EA5F894C',
        '#9B31924C',
        '#57167E4C',
        '#2B0B3F4C'
    ],
    balance : [
        '#017AC9',
        '#D43A3A',
        '#FD7B0A',
        '#EDEE55',
        '#63A92D',
        '#6C4BA4'
    ],
    balance_transparent : [
        '#017AC94C',
        '#D43A3A4C',
        '#FD7B0A4C',
        '#EDEE554C',
        '#63A92D4C',
        '#6C4BA44C'
    ],
    colorful : [
        '#ffec21',
        '#378aff',
        '#ffa32f',
        '#f54f52',
        '#93f03b',
        '#9552ea'
    ],
    colorful_transparent : [
        '#ffec214C',
        '#378aff4C',
        '#ffa32f4C',
        '#f54f524C',
        '#93f03b4C',
        '#9552ea4C'
    ],
    rainbow : [
        '#52D726',
        '#FFEC00',
        '#FF7300',
        '#FF0000',
        '#007ED6',
        '#7CDDDD'
    ],
    rainbow_transparent : [
        '#52D7264C',
        '#FFEC004C',
        '#FF73004C',
        '#FF00004C',
        '#007ED64C',
        '#7CDDDD4C'
    ]
};


const chartConfigList = {
    barConfig: (chartData) => {
        const config = {
            type : 'bar',
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        }

        config['data'] = chartData;

        return config;
    },
    donutOrPieConfig : (chartData) => {
        const config = {
            type : 'doughnut',
        }

        config['data'] = chartData;

        return config;
    }
};

const chartDataList = {
    barData : (fetchedData,colorScheme) => {
        const exampleData = {
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
            datasets:
            [
                {
                label: 'My First Dataset',
                data: [65, 59, 80, 81, 56, 55, 40],
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderColor: 'rgb(255, 99, 132)',
                borderWidth: 1
                },
                {
                    label: 'My Second Dataset',
                    data: [65, 59, 80, 81, 56, 55, 40],
                    backgroundColor: 'rgba(255, 159, 64, 0.2)',
                    borderColor: 'rgb(255, 205, 86)',
                    borderWidth: 1
                },
                {
                    label: 'My Third Dataset',
                    data: [65, 59, 80, 81, 56, 55, 40],
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgb(75, 192, 192)',
                    borderWidth: 1
                }
            ]
        };


        // data re-define
        let returnData;

        returnData['labels'] = labelList;

        fetchedData.map(item => {

        });
        data['labels'] = labelList;
        data.datasets['label'] = fetchedData.title;
        data.datasets['data'] = fetchedData.dataList;
        data.datasets['backgroundColor'] = colorList[colorScheme] || colorList['def'];

        return data;
    },
    donutOrPieData : (labelList,dataList,title,colorScheme) => {
        const data = {
            labels : [
                'Red',
                'Blue',
                'Yellow'
            ],
            datasets : [{
                label: 'My First Dataset',
                data: [300, 50, 100],
                backgroundColor: [
                    'rgb(255, 99, 132)',
                    'rgb(54, 162, 235)',
                    'rgb(255, 205, 86)'
                ],
                hoverOffset: 4
            }]
        }

        // data re-define
        data['labels'] = labelList;
        data.datasets['label'] = title;
        data.datasets['data'] = dataList;
        data.datasets['backgroundColor'] = colorList[colorScheme] || colorList['def'];

        return data;
    }
};


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
        backgroundColor: 'rgba(255, 99, 132, 0.2)',
        borderColor: 'rgb(255, 99, 132)',
        borderWidth: 1
    },
    {
        label: 'My Second Dataset',
        data: [65, 31, 70, 40, 56, 55, 40],
        backgroundColor: 'rgba(255, 159, 64, 0.2)',
        borderColor: 'rgb(255, 205, 86)',
        borderWidth: 1
    },
    {
        label: 'My Third Dataset',
        data: [65, 59, 80, 81, 56, 55, 40],
        backgroundColor: 'rgba(75, 192, 192, 0.2)',
        borderColor: 'rgb(75, 192, 192)',
        borderWidth: 1
    }]
};

var options1 = {
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
};

var options2 = {
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
};


const config = {
    type: 'doughnut',
    options : options1,
    data: donut_data
};

const config1 = {
    type: 'bar',
    options : options2,
    data: lineData
};

const list = [
    {
        "id" : 1,
        "name" : "home"
    },
    {
        "id" : 2,
        "name" : "car"
    }
];



// ***************************
// POS Sections
// ***************************

// Data Cache
const fetchCache = {}; // Cache by URL

// Order List
let orderDataList = {};

// Input Group
const productIdInput = document.querySelector('#productID');
const productNameInput = document.querySelector('#productName');
const productQtyInput = document.querySelector('#productQty');
const customerIdInput = document.querySelector('#customerID');
const pointCardIdInput = document.querySelector('#pointCardId');
const pointCardTypeSelection = document.querySelector('#pointCardType');

// Element Group
const itemList = document.querySelector("#item-list");
const orderList = document.querySelector('#order-list');
const orderAddBtn = document.querySelector('#orderAdd');
const orderResetBtn = document.querySelector('#orderReset');


// Point Card Search
PointCardDataSearch = async (searchItem,dataUrl) => {

    try{
        const res = await fetch(`${dataUrl}/${encodeURIComponent(searchItem)}`);
        const data = await res.json();
        if (data){
            return data;
        }
    }catch (err) {
        console.error(err);
        return null;
    }

    return null;
}

// Customer ID input field if 'Enter' pressed.
customerIdInput.addEventListener('keydown', async evt => {
    if (evt.key === 'Enter'){
        const url = customerIdInput.dataset.url;
        const customerId = customerIdInput.value;

        const data = await PointCardDataSearch(customerId,url);

        if (data){
            pointCardTypeSelection.innerHTML='';

            data.forEach(item => {
                const elementOption = document.createElement('option');
                elementOption.setAttribute('data-point-card-id',item['cardId']);
                elementOption.innerText = item['cardType'];

                pointCardTypeSelection.appendChild(elementOption);
            });

            // Set the first option as selected by default
            pointCardTypeSelection.selectedIndex = 0;

            // Trigger change event manually to update Point Card ID input
            pointCardTypeSelection.dispatchEvent(new Event('change'));

        }else{
            errorAlertDisplay('Customer Id not Found!')
        }

    }
});

// Point Card Type input field if changed.
pointCardTypeSelection.addEventListener('change',evt => {
    const currentOption = evt.target.selectedOptions[0];
    const pointCardId = currentOption.dataset['pointCardId'];
    pointCardIdInput.value = pointCardId;
});

// Point Card ID input field if 'Enter' pressed.
pointCardIdInput.addEventListener('keydown', async evt => {
    if (evt.key === 'Enter'){
        const url = pointCardIdInput.dataset.url;
        const pointCardId = pointCardIdInput.value;

        const data = await PointCardDataSearch(pointCardId,url);

        if (data){
            customerIdInput.value = data['customerId'];

            const elementOption = document.createElement('option');
            elementOption.setAttribute('data-point-card-id',data['cardId']);
            elementOption.setAttribute('selected','');
            elementOption.innerText = data['cardType'];

            pointCardTypeSelection.innerHTML='';
            pointCardTypeSelection.appendChild(elementOption);
        }else{
            errorAlertDisplay('Point Card Id not Found!')
        }
    }

});


// Product Data Search
productDataSearch = async (searchItem,dataList,dataUrl) => {

    for (const url in dataList) {
        const product = dataList[url].find(p => p.productId == searchItem);
        if (product) {
            return product;
        }
    }

    try{
        const res = await fetch(`${dataUrl}/${encodeURIComponent(searchItem)}`);
        const data = await res.json();
        if (data){
            return data;
        }
    }catch (err) {
        console.error(err);
            return null;
    }

    return null;
}

// Product Id Input Field If Enter Pressed
productIdInput.addEventListener('keydown',async evt => {
    if (evt.key === 'Enter'){
        const data = await productDataSearch(productIdInput.value,fetchCache,productIdInput.dataset.url);

        if (data){
            productNameInput.value = data.productName;
            productQtyInput.focus();
        } else{
            errorAlertDisplay("Your Product not found!")
        }
    }
});

// Product Qty Input Field If Enter Pressed
productQtyInput.addEventListener('keydown', evt => {
    if (evt.key === 'Enter'){
        orderAddBtn.click();
        productIdInput.focus();
    }
});

// Order List Add
orderAddBtn.addEventListener('click',async evt => {

    const url = orderAddBtn.dataset.url;

    const productId = productIdInput.value;
    const productQty = productQtyInput.value;

    let productDetail= await productDataSearch(productId,fetchCache,url);

    if (productDetail == null){
        errorAlertDisplay("Your Product not found!");
        return;
    }

    if(productQty === '' || productQty > productDetail.productCount){
        errorAlertDisplay("Quantity must not blank or could be Inventory quantities are lower than you value!");
        return;
    }

    let productName = productDetail.productName;
    let productPrice = productDetail.productPrice;

    let item = {
        'productId' : productId,
        'productName' : productName,
        'productQty' : productQty,
        'productPrice' : productPrice,
    }
    orderDataList[productId] = item;
    orderElementCreate(orderDataList,orderList);

    productIdInput.value = '';
    productNameInput.value = '';
    productQtyInput.value = '';
});


// Order List Element Create
function orderElementCreate(list,target){
    let totalPrice=0.0;
    target.innerHTML = '';
    let counter = 0;

    for (const item in list) {
        const tRow = document.createElement('tr');

        // For Data Submit
        const itemId = document.createElement('input');
        const itemQty = document.createElement('input');

        itemId.type='hidden';
        itemQty.type='hidden';

        itemId.id= `itemList[${counter}].productId`;
        itemQty.id= `itemList[${counter}].productQty`;

        itemId.name= `itemList[${counter}].productId`;
        itemQty.name= `itemList[${counter}].productQty`;

        itemId.value = list[item].productId;
        itemQty.value = list[item].productQty;

        tRow.appendChild(itemId);
        tRow.appendChild(itemQty);


        // For Row Remove
        const delEle = document.createElement('td');
        const delBtn = document.createElement('button');
        const trashCSV = document.createElement('i');

        trashCSV.className = 'bi bi-trash';

        delEle.className = 'p-0';
        delBtn.className = 'btn btn-danger p-1';
        delBtn.type='button';
        delBtn.setAttribute('data-id',list[item].productId);

        delBtn.appendChild(trashCSV);

        // List Remove Listener
        delBtn.addEventListener('click', ev => {
            const id = ev.currentTarget.dataset.id;
            delete list[id];

            orderElementCreate(list,target);
        });

        delEle.appendChild(delBtn);
        tRow.appendChild(delEle);


        // For Display Data
        const pName = document.createElement('td');
        const pQty = document.createElement('td');
        const pPrice = document.createElement('td');

        pName.innerText = list[item].productName;
        pQty.innerText = list[item].productQty;
        pPrice.innerText = list[item].productPrice;

        tRow.appendChild(pName);
        tRow.appendChild(pQty);
        tRow.appendChild(pPrice);

        target.appendChild(tRow);
        totalPrice += list[item].productQty * list[item].productPrice;
        counter++;

    }

    // For Display Footer
    const fRow = target.nextElementSibling;
    const formatter = new Intl.NumberFormat('en-US', {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    });
    fRow.children[0].children[1].innerText = formatter.format(totalPrice * 0.08);
    fRow.children[1].children[1].innerText = formatter.format(totalPrice);
}


// Item Select And Auto input
const itemsAutoInput= (item) => {
    item.addEventListener('click', evt => {
        productIdInput.value = item.dataset.id;
        productNameInput.value = item.dataset.name;
        productQtyInput.focus();
    });
};

// Item List Renderer
const itemListRender = (target,datalist,itemListener) => {

    if (datalist) {
        target.innerHTML='';
        target.previousElementSibling.classList.add('d-none');

        datalist.forEach(item =>{
            const spanEle = document.createElement('span');
            spanEle.innerText = item['productCount'];
            spanEle.className = 'badge text-bg-secondary';

            const buttonEle = document.createElement('button');

            buttonEle.className = 'list-group-item list-group-item-action text-bg-dark item-selection';
            buttonEle.innerText = item['productName'];
            buttonEle.type='button'
            buttonEle.setAttribute('data-id',item['productId']);
            buttonEle.setAttribute('data-name',item['productName']);
            buttonEle.append(spanEle);

            itemListener(buttonEle);

            itemList.appendChild(buttonEle);

        });
    }
}

// Category Item List Create
const categorySelection = document.querySelectorAll('.category-selection')
categorySelection.forEach(category => {
    category.addEventListener('click', evt => {
        evt.preventDefault();
        const url = category.dataset.url;

        // Call the API get the data and Cache
        if (!fetchCache[url]){
            fetch(url)
                .then(res => res.json())
                .then(data => {
                    fetchCache[url] = data;
                    itemListRender(itemList,data,itemsAutoInput);
                })
                .catch(err => {
                    const noDataElement = itemList.previousElementSibling;
                    noDataElement.classList.remove('d-none');
                    noDataElement.innerText ='No Data';
                    console.error(err);
                });
        }else{
            itemListRender(itemList,fetchCache[url],itemsAutoInput);
        }

    });
});

// Reset Button config
orderResetBtn.addEventListener('click',evt => {
    productIdInput.value = '';
    productNameInput.value = '';
    productQtyInput.value = '';
    customerIdInput.value = '';
    pointCardIdInput.value = '';

    orderList.innerHTML = '';
    pointCardTypeSelection.innerHTML='';
});

// Track focus on input fields
let lastFocusedInput = null;
document.querySelectorAll('input, textarea').forEach(input => {
    input.addEventListener('focus', evt => {
        evt.preventDefault();
        lastFocusedInput = input;
    });
});

// Screen keypads action
const screenKeyPads = document.querySelectorAll('.screenKeyPad');
screenKeyPads.forEach(item => {
    item.addEventListener('click',evt => {
        const elementText = item.innerText.trim();
        const isOnlyDigits = /^[0-9]+$/.test(elementText);

        if (!lastFocusedInput){
            return;
        }

        if (isOnlyDigits){
            // current focus input
            lastFocusedInput.value += elementText;
        } else if (item.dataset.btn === 'backspace'){
            // Action for backspace
            const inputValue = lastFocusedInput.value;
            lastFocusedInput.value = inputValue.slice(0,inputValue.length-1);
        } else if (item.dataset.btn === 'enter'){
            const event = new KeyboardEvent('keydown', {
                key: 'Enter',
                code: 'Enter',
                keyCode: 13,       // Deprecated, but still widely used
                which: 13,         // Deprecated too
                bubbles: true,     // Needed so the event bubbles up
                cancelable: true
            });

            lastFocusedInput.dispatchEvent(event);
        }

    });
});


// Input value is only input
const onlyNumbers = document.querySelectorAll('.only-number-input');
onlyNumbers.forEach(item => {
   item.addEventListener('input',evt =>{
       evt.preventDefault();
       item.value = item.value.replace(/[^0-9]/g, '');
   });
});


// Alert Item impl
const errorAlert = document.querySelector('#error-alert');
function errorAlertDisplay(content){
    errorAlert.children[0].innerText = content;
    errorAlert.classList.remove('d-none');
    errorAlert.classList.add('show');
}

errorAlert.children[1].addEventListener('click',evt => {
    errorAlert.classList.remove('show');
    errorAlert.classList.add('d-none');
});


// All Input Default Enter Dismissed
const posInput = document.querySelectorAll("input");
posInput.forEach(input => {
    input.addEventListener("keydown", evt => {
        if (evt.key === "Enter"){
            evt.preventDefault()
        }
    });
});

