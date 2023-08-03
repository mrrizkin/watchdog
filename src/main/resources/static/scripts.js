function openModal(e) {
    const target = e.getAttribute("target")
    document.getElementById(target).showModal()
}

function closeModal(e) {
    const target = e.getAttribute("target")
    document.getElementById(target).close()
}

document.querySelectorAll("form").forEach(form => {
    form.addEventListener("submit", submit)
})

function submit(e) {
    e.preventDefault()
    let hiddenList = [];

    let form = e.target;

    // make all unchecked checkboxes have a value of off
    form.querySelectorAll("input[type=checkbox]").forEach(checkbox => {
        if (!checkbox.checked) {
            const hidden = document.createElement("input")
            hidden.setAttribute("type", "hidden")
            hidden.setAttribute("name", checkbox.getAttribute("name"))
            hidden.setAttribute("value", "off")
            hiddenList.push(hidden)
            checkbox.append(hidden)
        }
    })

    const formData = new FormData(form)
    const body = {}
    for (let [key, value] of formData.entries()) {
        if (key.endsWith("[]")) {
            key = key.slice(0, -2)
            if (!body[key]) {
                body[key] = []
            }
            body[key].push(value)
            continue
        }

        body[key] = value
    }

    hiddenList.forEach(hidden => {
        hidden.remove()
    })

    let action = form.getAttribute("action")
    const method = form.getAttribute("method")

    const matches = action.match(/\{(\w+)\}/)
    if (matches) {
        const actionData = e.target.getAttribute("data-action")
        action = action.replaceAll(`{${matches[1]}}`, actionData)
    }

    fetch(action, {
        method: method,
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(body),
    })
        .then(response => response.json())
        .then(response => {
            if (response.success) {
                location.reload()
            }
        })
}
