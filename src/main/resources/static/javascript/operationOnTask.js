for(let edit of document.querySelectorAll(".edit")) {
    edit.addEventListener("click", (e) => {
        let parent = e.target.parentNode;
        setBlur("10px");
        createShowForm(parent.querySelector(".title").innerText, parent.querySelector(".description").innerText, parent.parentNode.id, parent.id, "Edit Task", edit);
        sendForm(`http://localhost:8095/api/user/edit-task/${parent.querySelector(".id").innerText}?idTaskCollection=${currentList.id}`, "PUT");
    });
}

document.querySelector(".btn").addEventListener("click", () =>{
    setBlur("10px");
    createShowForm("", "", "", "", "Add Task", document.querySelector(".btn"));
    sendForm(`http://localhost:8095/api/user/create-task?idTaskCollection=${currentList.id}`, "POST");
})

function createShowForm(title, description, statusTask, priorityTask, button, element){
    let content = document.querySelector(".content");
        let contentDiv = document.createElement("div");
        contentDiv.classList.add("contentDiv");
        contentDiv.appendChild(createForm(title, description, statusTask, priorityTask, button));
    content.appendChild(contentDiv);
    document.addEventListener("click", function (e) {
        if (!contentDiv.contains(e.target) && element !== e.target) {
            if (content.contains(contentDiv)) {
                setBlur(0);
                content.removeChild(contentDiv);
            }
        }
    });
}

function createForm(title, description, statusTask, priorityTask, button){
    let form = document.createElement("form");
    form.classList.add("form");
        let csrfInput = document.createElement("input");
        csrfInput.type = "hidden";
        csrfInput.name = "_csrf.parameterName";
        csrfInput.value = "_csrf.token";
    form.appendChild(csrfInput);
    form.appendChild(createTextInput("Title", title));
    form.appendChild(createTextInput("Description", description));
    form.appendChild(createRadioInput(["TODO", "IN_PROGRESS", "DONE", "CANCELED"], "StatusTask", statusTask));
    form.appendChild(createRadioInput(["LOW", "MEDIUM", "HIGH"], "PriorityTask", priorityTask));
        let submitButton = document.createElement("input");
        submitButton.type = "submit";
        submitButton.value = button;
    form.appendChild(submitButton);
    return form;
}

function sendForm(url, method){
    document.querySelector('.form').addEventListener("submit", function (event) {
        event.preventDefault();
        setBlur(0);
        fetch(url, {
            method: method,
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(dataForm())
        }).then(response => response.json()).then(() => {
            window.location.href = `/list/${currentList.id}`;
        })
    });
}

function dataForm(){
    return {
        priorityTask: CheckedRadio("PriorityTask"),
        statusTask: CheckedRadio("StatusTask"),
        title: document.querySelector('input[name = "Title"]').value,
        description: document.querySelector('input[name = "Description"]').value,
    };
}

function createTextInput(name, value){
    let element = document.createElement("div");
    element.classList.add(name);
    element.appendChild(textInput(name, value));
    element.appendChild(textLabel(name));
    return element;
}

function textInput(name, value){
    let textInput = document.createElement("input");
    textInput.type = "text";
    textInput.required = true;
    textInput.name = name;
    textInput.value = value;
    textInput.classList.add("input");
    return textInput;
}

function textLabel(name){
    let textLabel = document.createElement("label");
    textLabel.classList.add("label" + name);
    textLabel.innerText = name;
    return textLabel;
}

function createRadioInput(elements, name, value){
    let div = document.createElement("div");
    div.classList.add("RadioInput");
    for (let element of elements) {
        let divElement = document.createElement("div");
        divElement.classList.add("element");
        divElement.appendChild(radioLabel(element, name, value));
        div.appendChild(divElement);
    }
    return div;
}

function radioLabel(element, name, value){
    let label = document.createElement("label");
    label.classList.add("label" + name);
    label.htmlFor = element;
    label.appendChild(radioInput(element, name, value));
    label.appendChild(document.createTextNode(element));
    return label
}

function radioInput(element, name, value){
    let input = document.createElement("input");
    input.type = "radio";
    input.name = name;
    input.id = element;
    input.value = element;
    input.required = true;
    if(element === value){
        input.checked = true;
    }
    return input;
}

function setBlur(blur){
    for(let box of boxes){
        box.style.filter = `blur(${blur})`;
    }
}

function CheckedRadio(nameName) {
    const radioButtons = document.querySelectorAll('input[name=' + nameName + ']');
    for (let i of radioButtons) {
        if (i.checked) {
            return i.value;
        }
    }
}