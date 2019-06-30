/**
 * Функция валидации введенных пользователем данных,
 * проверяет введен ли заголовок и текст заметки.
 * Если поле заголовок не заполнено, выводит сообщение с просьбой заполнить поле.
 * Допустимая длина заголовка заметки 200 символов.
 * Допустимая длина текста заметки 1500 символов.
 * @param title  заголовок заметки
 * @param description текст заметки
 * @returns {boolean}
 */
function validate(title, description) {
    var result = true;
    if (description == '') {
        result = false;
        alert('Пожалуйста, введите текст заметки');
    }
    if (title == '') {
        alert('Вы не ввели текст заметки, при просмотре в качестве заголовка заметки будут использованы первые 200 символов текста заметки');
    }
    if (title.length > 200) {
        result = false;
        alert('Длинна заголовка заметки не должна превышать 200 символов, вы ввели ' + title.length + ' символов');
    }
    if (description.length > 1500) {
        result = false;
        alert('Длинна текста заметки не должна превышать 1500 символов, вы ввели ' + description.length + ' символов');
    }
    return result;
}

/**
 * Функция для создания заметки и последующего сохранения в базу данных.
 * Вызывает validate() для проверки введенных данных, закрывает модальное окно и
 * отправляет POST запрос в add сервлет контроллера NoteController.
 */
function addNote() {
    var title = $('#title').val();
    var description = $('#description').val();
    if (validate(title, description) == true) {
        $('#myModal').modal('hide');
        $.ajax({
            url: "/add",
            method: "POST",
            data: {
                title: title,
                description: description
            },
            complete: function () {
                showAll();
                $('#title').val('');
                $('#description').val('');
            }
        });
    }
}

/**
 * Функция поиска заметки по заданной подстроке.
 * Отправляет POST запрос в notes сервлет контроллера NoteController.
 * В ответ получает массив заметок в виде JSON.
 * Передает массив заметок в функцию fillTableAfterResponse.
 */
function findNotes() {
    var start = new Date();
    var stringToSearch = $('#search').val();
    $.ajax({
        url: "/notes",
        method: "POST",
        data: {
            stringToSearch: stringToSearch
        },
        complete: function (response) {
            var notes = JSON.parse(response.responseText);
            fillTableAfterResponse(notes);
        }
    });
    // document.getElementById("searchTime").innerHTML = 'Поиск выполнен за ' + (new Date() - start) + ' мс';
}

/**
 * Функция вывода всех заметок.
 * Отправляет GET запрос в notes сервлет контроллера NoteController.
 * В ответ получает массив заметок в виде JSON.
 * Передает массив заметок в функцию fillTableAfterResponse.
 */
function showAll() {
    $.ajax({
        url: "/notes",
        method: "GET",
        complete: function (response) {
            var notes = JSON.parse(response.responseText);
            $('#search').val('');
            document.getElementById("searchTime").innerHTML = '';
            fillTableAfterResponse(notes);
        }
    });
}

/**
 * Функция для удаления заметки по id.
 * Отправляет POST запрос в delete сервлет контроллера NoteController.
 * Если поле для поиска пустое, то вызовет showAll() для показа всех заметок.
 * Если поле для поиска не пустое, то вызовет findNotes() для поиска заметок по вхождению подстроки.
 * @param id заметки
 */
function deleteNote(id) {
    $.ajax({
        url: "/delete",
        method: "POST",
        data: {
            id: id
        },
        complete: function () {
            var searchString = $('#search').val();
            if (searchString == '') {
                showAll();
            } else {
                findNotes();
            }
        }
    });
}

/**
 * Функция для обновления заметки по id и последующего сохранения в базу данных.
 * Отправляет POST запрос в update сервлет контроллера NoteController.
 * Если поле для поиска пустое, то вызовет showAll() для показа всех заметок.
 * Если поле для поиска не пустое, то вызовет findNotes() для поиска заметок по вхождению подстроки.
 * @param id заметки
 */
function updateNote(id) {
    var updatedTitle = $('#title' + id).val();
    var updatedDesc = $('#description' + id).val();
    if (validate(updatedTitle, updatedDesc) == true) {
        $('#myModal' + id).modal('hide');
        $.ajax({
            url: "/update",
            method: "POST",
            data: {
                id: id,
                title: updatedTitle,
                description: updatedDesc
            },
            complete: function () {
                var searchString = $('#search').val();
                if (searchString == '') {
                    showAll();
                } else {
                    findNotes();
                }
            }
        });
    }
}

/**
 * Функция для заполнения таблицы заметок на html странице.
 * Если для заметки не указан заголовок, то отображаются первые 200 символов текста заметки.
 * @param notes массив заметок
 */
function fillTableAfterResponse(notes) {
    $("#dynamic td").parent().remove();
    if ($('#search').val() === '') {
        document.getElementById("searchTime").innerHTML = '';
    }
    if (notes.length == 0) {
        document.getElementById("searchResult").innerHTML = 'Совпадений не обнаружено';
    } else {
        document.getElementById("searchResult").innerHTML = '';
        for (var i = 0; i < notes.length; i++) {
            var id = notes[i].id;
            var title = notes[i].title;
            var description = notes[i].description;
            var titleToShow = title == '' ? description.substring(0, 200) : title;
            $('#dynamic tr:last').after(
                '<tr>' +
                '<td width="300" valign="middle" align="left">' + titleToShow + '</td>' +
                '<td width="300" valign="middle">' +
                '<div class="btn-group">' +
                '<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#myModal' + id + '">Посмотреть заметку</button>' +
                '<div class="modal fade" id="myModal' + id + '" role="dialog">' +
                '<div class="modal-dialog modal-lg">' +
                '<div class="modal-content">' +
                '<div class="modal-header">' +
                '<button type="button" class="close" data-dismiss="modal">&times;</button>' +
                '<h4 class="modal-title">Заметка :</h4>' +
                '</div>' +
                '<div class="modal-body">' +
                '<form>' +
                '<div class="form-group">' +
                '<label for="title' + id + '">Заголовок:</label>' +
                '<input type="text" class="form-control" id="title' + id + '" value="' + title + '">' +
                '</div>' +
                '<div class="text-area' + id + '">' +
                '<label for="description' + id + '">Заметка:</label>' +
                '<textarea class="form-control" rows="4" id="description' + id + '"></textarea>' +
                '</div>' +
                '</form>' +
                '</div>' +
                '<div class="modal-footer">' +
                '<button type="button" class="btn btn-danger" id="button' + id + '" ' +
                'value="' + id + '" onclick="$(updateNote(this.value))">Сохранить изменения</button>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '<button type="button" class="btn btn-danger" value="' + id + '" di="" onclick="$(deleteNote(this.value))">Удалить</button>' +
                '</div>' +
                '</td>' +
                '</tr>'
            );
            $("#description" + id).val(description);
        }
    }
}