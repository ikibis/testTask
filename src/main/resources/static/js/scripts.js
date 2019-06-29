function validate() {
    var result = true;
    var desc = $('#description').val();
    if (desc == '') {
        result = false;
        alert('Пожалуйста, введите текст заметки');
    }
    return result;
}

function addNote() {
    if (validate() == true) {
        var title = $('#title').val();
        var description = $('#description').val();
        $.ajax({
            url: "/add",
            method: "POST",
            data: {
                title: title,
                description: description
            },
            complete: function () {
                showAll();
                $('#myModal').modal('hide');
                $('#title').val('');
                $('#description').val('');
            }
        });
    }
}

function findNotes() {
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
}

function showAll() {
    $.ajax({
        url: "/notes",
        method: "GET",
        complete: function (response) {
            var notes = JSON.parse(response.responseText);
            $('#search').val('');
            fillTableAfterResponse(notes);
        }
    });

}

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

function fillTableAfterResponse(notes) {
    $("#dynamic td").parent().remove();
    if (notes.length == 0) {
        document.getElementById("searchResult").innerHTML = 'Совпадений не обнаружено';
    } else {
        document.getElementById("searchResult").innerHTML = '';
        for (var i = 0; i < notes.length; i++) {
            var id = notes[i].id;
            var title = notes[i].title;
            var description = notes[i].title;
            $('#dynamic tr:last').after(
                '<tr>' +
                '<td width="300" valign="middle" align="left">' + title + '</td>' +
                '<td width="300" valign="middle" align="center">' +
                '<div class="btn-group">' +
                '<button type="button" class="btn btn-danger" value="' + id + '" onclick="$(deleteNote(this.value))">Удалить </button>' +
                '</div>' +
                '</td>' +
                '</tr>'
            );
        }
    }
}