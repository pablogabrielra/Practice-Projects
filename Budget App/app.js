//BUDGET CONTROLLER
var budgetController = (function() {
    
    var Expense = function(id, description, value) {
        this.id =  id;
        this.description = description;
        this.value = value;
        this.percentage = -1;
    };

    Expense.prototype.calcPercentage = function(totalIncome) {
        if (totalIncome > 0) {
            this.percentage = Math.round((this.value / totalIncome) * 100);
        } else {
            this.percentage = -1;
        }
    };

    Expense.prototype.getPercentage = function() {
        return this.percentage;
    };

    var Income = function(id, description, value) {
        this.id =  id;
        this.description = description;
        this.value = value; 
    };
    
    var calculateTotal = function(type) {
        var sum = 0;
        data.allItems[type].forEach(function(cur){
            sum += cur.value;
        });
        data.totals[type] = sum;
    };


    var data = {
        allItems: {
            exp:[],
            inc:[],
        },
        totals: {
            exp: 0,
            inc: 0,
        },
        budget: 0,
        percentage: -1
        
    };
    
    return {
        addItem: function(type, des, val){
            var newItem, ID;
         
            //array position----[0,1,2,3,4,5]
            //assigned id ------[2,4,5,6,7,8]
            //ID = last ID + 1
            
            //Create a new ID
            if (data.allItems[type].length > 0) {
            ID = data.allItems[type][data.allItems[type].length -1].id + 1;
            } else {
                ID = 0;
            }
            //Create new item based on 'inc' or 'exp' type
            if (type === 'exp') {
                newItem = new Expense(ID, des, val);
            } else if (type === 'inc'){
                newItem = new Income(ID, des, val);
            }
            
            //Push it into our data structure
            data.allItems[type].push(newItem);
            
            //Return the new element
            return newItem;
        },
        calculateBudget: function() {

            //Calculate total income and expenses
            calculateTotal('exp');
            calculateTotal('inc');

            //Calculate the budget: income - expenses
            data.budget = data.totals.inc - data.totals.exp;

            //Calculate the percentage of income that we spent
            if (data.totals.inc > 0) {
            data.percentage = Math.round(data.totals.exp / data.totals.inc * 100);
            } else {
                data.percentage = -1;
            }

        },

        calculatePercentages: function() {
            data.allItems.exp.forEach(function(cur){
                cur.calcPercentage(data.totals.inc);
            });
        },

        getPercentage: function() {
            var allPerc = data.allItems.exp.map(function(cur) {
                return cur.getPercentage();
            });
            return allPerc;
        },

        deleteItem: function(type, id) {
            var ids, index;
             ids = data.allItems[type].map(function(current) { //The map function is like a foreach. and we set function to the current array
                 return current.id; //it will loop through every value and create the array in ids.
             });
             index = ids.indexOf(id); //we search the position of the id in the newly created array
 
             if(index !== -1) {
                 data.allItems[type].splice(index,1); //the -1 is when indexOf returns a non existent value in the array. Splice is used to remove a value, using the positioning as a indicator
             }
         },

        getBudget: function() {
            return {
                budget: data.budget,
                totalInc: data.totals.inc,
                totalExp: data.totals.exp,
                percentage: data.percentage
            }
        },

    };
     
})();


//UI CONTROLLER
var UIController = (function(){
    var DOMstrings = {
        inputType: '.add__type',
        inputDescription: '.add__description',
        inputValue: '.add__value',
        inputBtn: '.add__btn',
        incomeContainer: '.income__list',
        expensesContainer: '.expenses__list',
        budgetLabel: '.budget__value',
        incomeLabel: '.budget__income--value',
        expenseLabel:'.budget__expenses--value',
        percentageLabel: '.budget__expenses--percentage',
        container: '.container',
        expensesPercentageLabel: '.item__percentage',
        dateLabel: '.budget__title--month'

    };

    var formatNumber= function(num, type){
        var numSplit, int, dec, amountOfCommas, commaPosition;
        num = Math.abs(num);
        num = num.toFixed(2);
        numSplit = num.split('.');
        int = numSplit[0];
        commaPosition = 0;
        if (int.length > 3) {
            int.length % 3 !== 0 ? amountOfCommas = Math.floor(int.length/3) : amountOfCommas = int.length/3 - 1;
            for(var i = 0; i < amountOfCommas; i++) {
                int = int.substr(0, int.length - (3 + commaPosition)) + ',' + int.substr(int.length - (3 + commaPosition) , (3 + commaPosition));
                commaPosition += 4;
            }
        }
        dec = numSplit[1];
        return (type === 'exp' ? '-' : '+') + int  + '.' + dec;
    };
    var nodeListForEach = function(list, callback) {
        for (var i = 0; i < list.length; i++) {
            callback(list[i], i);
        }
    };
    return {
        getInput: function() {
            return {
                type: document.querySelector(DOMstrings.inputType).value, //Will be either inc or exp
                description: document.querySelector(DOMstrings.inputDescription).value,
                value: parseFloat(document.querySelector(DOMstrings.inputValue).value)//ParseFloat converts string to numbers
                
            };
            
        },
        
        addListItem: function(obj, type){ //obj is the newItem we created and type is the inc or exp.
            var html, newHtml, element;
            //We store the class of the html containter in the DOMstring (this is where we want to input our modified html), then we assign it to 'element'
            //Create HTML string with placeholder text
            
            if(type === 'inc'){
                element = DOMstrings.incomeContainer;
                //this is the html we want to insert depneding on inc or exp
                html = '<div class="item clearfix" id="inc-%id%"> <div class="item__description">%description%</div><div class="right clearfix"> <div class="item__value">%value%</div> <div class="item__delete"> <button class="item__delete--btn"><i class="ion-ios-close-outline"></i></button> </div></div></div>'; 
                //JS interprets single and double quotes seperatly. In this html code we put the whole code in single quotes, so it doesnt interfere with the doublequotes inside. If single quotes were used inside, we would you double quotes outside.            
            } else if (type === 'exp') {
                element = DOMstrings.expensesContainer;
                html = '<div class="item clearfix" id="exp-%id%"> <div class="item__description">%description%</div> <div class="right clearfix"> <div class="item__value">%value%</div> <div class="item__percentage">21%</div> <div class="item__delete"> <button class="item__delete--btn"><i class="ion-ios-close-outline"></i></button> </div> </div> </div>';
            }
            
            //Replace the placeholder text with some actual data          
            newHtml = html.replace('%id%', obj.id); //replace is a method for string. It searches for the string and replaces it ej:('string we want to find', 'string we want to replace it with)
            newHtml = newHtml.replace('%description%', obj.description);
            newHtml = newHtml.replace('%value%', formatNumber(obj.value, type));
            
            
            //Insert the HTML into the DOM
            document.querySelector(element).insertAdjacentHTML('beforeend', newHtml); //we select the containter we with to insert the code and then choose to put it before or after the end or begining, then we choose the html we insert
            
        },
    
        getDOMstrings: function(){
            return DOMstrings;
        },
        
        clearFields: function(){
            var fields, fieldsArr;
            
            fields = document.querySelectorAll(DOMstrings.inputDescription + ', ' + DOMstrings.inputValue); 
            //we use querySelectorAll so we wont have to querySelector twice. We put ', ' because its like the css syntax. Thats how we seperate the 2 values.
            //But the problem is that the querySelectorAll method doesnt return an array, but a list. A list is similar to an array but it doesnt hace certain 
            //methods. So we convert it to an array. We can use a trick using an array method, slice. It returns a copy of the array that its called on. 
            //We can trick the method so that we can pass a list into it and make it return an array.
            fieldsArr = Array.prototype.slice.call(fields);
            //we need to depend on the prototype attached to the array, since the slice method is stored in the array prototype. So we can call the method
            // and set 'fields' so the list points to the prototype, so we can use the function on a list.
            
            fieldsArr.forEach(function(current, index, array) {
                current.value = "";//We just need to set it to empty
            //the forEach method is an alternative to the for loop. In this case, it applys the function for each of the elements in the array. We also have access to the current value insde the arry, the index number of the value and the arry itself(it would be fieldsArr)
            //We could just simplify this since we only need to get rid of 2 fields, but it is useful if need to take care or even more fields.   
            
            fieldsArr[0].focus();//.focus just sets the focus on the corresponding field. In this case we want it on the description box. Which is the first element of the fields array.
            })
        },

        displayBudget: function(obj) {
            var type;
            obj.budget > 0 ? type = 'inc' : type = 'exp';

            document.querySelector(DOMstrings.budgetLabel).textContent = formatNumber(obj.budget, type);
            document.querySelector(DOMstrings.incomeLabel).textContent = formatNumber(obj.totalInc, 'inc');
            document.querySelector(DOMstrings.expenseLabel).textContent = formatNumber(obj.totalExp, 'exp');

            if(obj.percentage > 0){
                document.querySelector(DOMstrings.percentageLabel).textContent = obj.percentage + "%";
            } else {
                document.querySelector(DOMstrings.percentageLabel).textContent = "-";
            }
        },

        deleteListItem: function(selectorId) {
            var el = document.getElementById(selectorId);
            document.getElementById(selectorId).parentNode.removeChild(el);
        },

        displayPercentages: function(percentages) {

            var fields = document.querySelectorAll(DOMstrings.expensesPercentageLabel);

            nodeListForEach(fields, function(current, index) {
                if (percentages[index] > 0) {
                    current.textContent = percentages[index] + '%';
                } else {
                    current.textContent = '---';
                }

            });
        },

        displayMonth: function() {
            var now, year, month, months;
            months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
            now = new Date();
            year = now.getFullYear();
            month = months[now.getMonth()];
            document.querySelector(DOMstrings.dateLabel).textContent = month + ' of ' + year;


        },

        changedType: function() {
            var fields = document.querySelectorAll(
                DOMstrings.inputType + ',' +
                DOMstrings.inputDescription + ',' +
                DOMstrings. inputValue);

            nodeListForEach(fields, function(cur){
                cur.classList.toggle('red-focus');
            });
            document.querySelector(DOMstrings.inputBtn).classList.toggle('red');
            },

};
})();




// GLOBAL APP CONTROLLER
var controller = (function(budgetCtrl, UICtrl){
    
    var setupEventListeners = function() {
         
        var DOM = UICtrl.getDOMstrings();
        document.querySelector(DOM.inputBtn).addEventListener('click', ctrlAddItem);
    
        document.addEventListener('keypress', function(event) {
        
        if (event.keycode === 13 || event.which === 13) {
            ctrlAddItem();
        }
    });
        document.querySelector(DOM.inputType).addEventListener('change', UICtrl.changedType);
        document.querySelector(DOM.container).addEventListener('click', ctrlDeleteItem);

    };
    
    //we are setting the budget calculation and display in another function because later we will have to use it again when we need to subtract.
    var updateBudget = function() {
        //1. Calculate the budget
        
        budgetCtrl.calculateBudget();

        //2. Return the budget
        var budget = budgetCtrl.getBudget();
        
        //3. Display the budget on the UI

        UICtrl.displayBudget(budget);
        
    };

    var updatePercentages = function() {
        //1. Calculate percentages

        budgetCtrl.calculatePercentages();

        //2. Read percentages from the budget controller

        var percentages = budgetCtrl.getPercentage();

        //3. Update the UI with the new percentages

        UICtrl.displayPercentages(percentages);
    };

    //this is the main controller of the program
    var ctrlAddItem = function(){
        //1. Get the the field input data
        var input, newItem;
        input = UICtrl.getInput();
        
        //We set this If, so we can only activate the event if we have a description written and a value > 0. It doesnt work if we dont have anything written inside.
        if (input.description !== "" && !isNaN(input.value) && input.value > 0) {    //isNaN(input.value) returns true or false "Not a Number"
            //2. Add item to budget controller
        
            newItem = budgetCtrl.addItem(input.type, input.description, input.value);
        
            //3. Add the new item to the UI
        
            UICtrl.addListItem(newItem,input.type);
        
            //4. Clear the fields
        
            UICtrl.clearFields();
        
            //5. Calculate and update budget
        
            updateBudget();

            //6. Calculate and update percentages

            updatePercentages()
        }
    };
    var ctrlDeleteItem = function(event) { //we use the event object because we want to know what the target element is.
        var itemId, splitId, type, ID;   
        itemId = event.target.parentNode.parentNode.parentNode.parentNode.id;
        if (itemId) {
            
            //Separating the inc-1 (the row id) to show only the type and the id. We use the .split method of the string object.

            splitId = itemId.split('-');
            type = splitId[0];
            ID = parseInt(splitId[1]); 
            //1. Delete the item from the data structure

            budgetCtrl.deleteItem(type,ID);

            //2. Delete item from the UI

            UICtrl.deleteListItem(itemId);

            //3. Update and show the new budget

            updateBudget();

            //4. Update expense percentage

            updatePercentages();

        }
    };
    
    return {
        init: function() {
            console.log('Application has started.');
            UICtrl.displayMonth();
            UICtrl.displayBudget({
                budget: 0,
                totalInc: 0,
                totalExp: 0,
                percentage: -1});
            setupEventListeners();
        }
    };
    
})(budgetController, UIController);

controller.init();




















