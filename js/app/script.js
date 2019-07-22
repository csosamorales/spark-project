var app = document.getElementById("root")
var logo = document.createElement("img")

logo.src = "logo.png"

var container = document.createElement("div")
container.setAttribute("class", "boss-box")

app.appendChild(logo)
app.appendChild(container)

var request = new XMLHttpRequest()

request.open("GET", "https://api.mercadolibre.com/sites", true)

var containerSites = document.createElement("div")
containerSites.setAttribute("class", "siteBox")
var label = document.createElement("label")
label.textContent = "Seleccione site "

containerSites.appendChild(label)

request.onload = function (ev) {

    var data = JSON.parse(this.response)
    var select = document.createElement("select")
    var optionCero = document.createElement("option");
    optionCero.text = "-- Seleccionar --"
    optionCero.value = "0"
    select.add(optionCero)

    if (request.status >= 200 && request.status < 400) {
        data.forEach(function (site) {
            var option = document.createElement("option");
            option.text = site.name;
            option.value = site.id
            select.add(option);
        })
    } else {
        var errorMessage = document.createElement("marquee")
        errorMessage.textContent = "status code = " + data.status + " - message = " + data.message
        app.appendChild(errorMessage)
    }
    containerSites.appendChild(select)
    select.onchange = function (ev1) {
        showCategory(select.value)
    }
    app.appendChild(containerSites)
}

request.send()

var counter = 1


function showCategory(idSite) {

    //var idSite = select.value
    container.innerHTML = ""

    request.open("GET", "https://api.mercadolibre.com/sites/" + idSite + "/categories", true)

    request.onload = function (ev) {
        var data = JSON.parse(this.response)
        var boxCategories = document.createElement("div")
        boxCategories.id = counter + ""
        boxCategories.setAttribute("class", "category-box")
        var h3 = document.createElement("h3")
        h3.textContent = "Categorias MercadoLibre Argentina"
        if (request.status >= 200 && request.status < 400) {
            data.forEach(function (category) {

                var categoryItemList = document.createElement("li")

                var a = document.createElement("a")
                a.setAttribute("class", "cursor-pointer")
                a.textContent = category.name
                a.onclick = function (ev1) {
                    showChildrens(category.id, category.name)
                }

                var span = document.createElement("span")
                span.setAttribute("class", "cursor-pointer")
                span.textContent = "🔍"
                span.onclick = function (ev1) {
                    showChildrens(category.id, category.name)
                }

                categoryItemList.appendChild(a)
                categoryItemList.appendChild(span)

                boxCategories.appendChild(categoryItemList)

            })
        } else {
            var errorMessage = document.createElement("marquee")
            errorMessage.textContent = "status code = " + data.status + " - message = " + data.message
            app.appendChild(errorMessage)
        }
        container.appendChild(boxCategories)
        app.appendChild(container)
    }

    request.send()
}

function showChildrens(id, categoryName, idDiv) {


    request.open("GET", "https://api.mercadolibre.com/categories/" + id, true)
    request.onload = function (ev) {
        var data = JSON.parse(this.response)
        if (data.children_categories.length > 0) {
            if (idDiv < counter) {
                //hay que borrar los hijos
                deleteChildrens(idDiv)
                counter = idDiv
            }
            counter++
            var boxCategories = document.createElement("div")
            boxCategories.id = counter
            boxCategories.setAttribute("class", "category-box")
            var h3 = document.createElement("h3")
            h3.textContent = categoryName
            if (request.status >= 200 && request.status < 400) {
                data.children_categories.forEach(function (category) {

                    var categoryItemList = document.createElement("li")

                    var a = document.createElement("a")
                    a.setAttribute("class", "cursor-pointer")
                    a.textContent = category.name
                    var id = counter
                    a.onclick = function (ev1) {
                        showChildrens(category.id, category.name, id)
                    }

                    var span = document.createElement("span")
                    span.setAttribute("class", "cursor-pointer")
                    span.textContent = "🔍"
                    span.onclick = function (ev1) {
                        showChildrens(category.id, category.name, id)
                    }

                    categoryItemList.appendChild(a)
                    categoryItemList.appendChild(span)

                    boxCategories.appendChild(categoryItemList)

                })
            } else {
                var errorMessage = document.createElement("marquee")
                errorMessage.textContent = "status code = " + data.status + " - message = " + data.message
                app.appendChild(errorMessage)
            }
            container.appendChild(boxCategories)
            app.appendChild(container)
        } else {
            alert("No hay mas categorias")
        }

    }

    request.send()
}

function deleteChildrens(idDiv) {

    for (var i = idDiv+1; i <= counter; i++) {
        var children = document.getElementById(i)
        children.remove()
    }

}
