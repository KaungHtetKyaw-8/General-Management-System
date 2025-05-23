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
const customerIdInput = document.querySelector('#customerId');
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

