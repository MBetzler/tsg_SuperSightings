/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    loadSightings();
    loadEntities();
    loadLocations();


    // $('#locNav').offset({ top: $('#locPH').height() });
    // var locNavOffset = $('#locNav').offset();
    // $('#locContainer').offset({ top: locNavOffset.top + $('#locNav').height() });

    // showHeight($('#addSigDescription').height());
    // showHeight(addSigAddress);
    $('#addSighting').click(function (event) {

        clearAddErrors();

        if (formValidate($('#addSigForm')[0])) {
            return false;
        }

        // var sigContainer = $('#sigContainer');

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/SuperSightings/sighting',
            data: JSON.stringify({
                locationId: $('#selectSigLocation').val(),
                entityIds: $('#selectSigEntities').val(),
                sightingDateTime: $('#sigDateTime').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json',
            success: function (data, status) {
                document.getElementById('addSigForm').classList.remove('was-validated');
                $('#selectSigLocation').val('');
                $('#selectSigEntities').val('');

                clearSightings();
                loadSightings();
            },
            error: function (errorResponse) {
                $('#addSigErrors').show();
                var response = $.parseJSON(errorResponse.responseText).message;
                var errorArray = new Array();
                errorArray = response.split(',');
                // response = JSON.parse(response.responseText).message;
                for (var i = 0; i < errorArray.length; i++) {
                    $('#addSigErrors')
                        .append($('<p>')
                            .attr({ class: 'small', style: 'color: red; padding: 0; margin-top: 0; margin-bottom: 0;' })
                            // .text('Error calling web service.  Please try again later.'));
                            .text(errorArray[i]));
                }
                // $('#addSigErrors').show();
                // sigContainer
                //     .attr({ class: 'container' })
                //     .append($('<ul>')
                //         .attr({ class: 'list-group' })
                //         .append($('<li>')
                //             .attr({ class: 'list-group-item list-group-item-danger', style: 'width: 92.5%, margin: auto;' })
                //             .text('Error calling SuperSightings web service.  Please try again later.')));
            }

        });
    });

    $('#saveSigEdit').click(function (event) {

        clearEditErrors();

        if (formValidate($('#editSigForm')[0])) {
            return false;
        }

        // var sigContainer = $('#sigContainer');

        $.ajax({
            type: 'PUT',
            url: 'http://localhost:8080/SuperSightings/sighting/' + $('#editSigId').val(),
            data: JSON.stringify({
                sightingId: $('#editSigId').val(),
                locationId: $('#editSigLoc').val(),
                entityIds: $('#editSigEnts').val(),
                sightingDateTime: $('#editSigDateTime').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json',
            success: function (data, status) {
                document.getElementById('editSigForm').classList.remove('was-validated');
                $('#editSigDiv').fadeOut("slow", "linear", function () {
                    // document.getElementById('editSigForm').classList.remove('was-validated');
                    $('#editSigName').val('');
                    $('#editSigLoc').val('');
                    $('#editSigEnts').val('');
                });

                $('#fadeSigMainDiv').fadeTo("fast", 0, function () {
                    clearSightings();
                    loadSightings();
                    document.getElementById('fadeSigMainDiv').style.pointerEvents = "auto";
                });

                $('#fadeSigMainDiv').fadeTo("slow", 1);

            },
            error: function (errorResponse) {
                $('#editSigErrors').show();
                var response = $.parseJSON(errorResponse.responseText).message;
                var errorArray = new Array();
                errorArray = response.split(',');
                // response = JSON.parse(response.responseText).message;
                for (var i = 0; i < errorArray.length; i++) {
                    $('#editSigErrors')
                        .append($('<p>')
                            .attr({ class: 'small', style: 'color: red; padding: 0; margin-top: 0; margin-bottom: 0;' })
                            // .text('Error calling web service.  Please try again later.'));
                            .text(errorArray[i]));
                }


                // sigContainer
                //     .attr({ class: 'container' })
                //     .append($('<ul>')
                //         .attr({ class: 'list-group' })
                //         .append($('<li>')
                //             .attr({ class: 'list-group-item list-group-item-danger', style: 'width: 92.5%, margin: auto;' })
                //             .text('Error calling SuperSightings web service.  Please try again later.')));
            }

        });
    });

});

function defaultDateTime() {
    if (document.getElementById('addSigNavButton').getAttribute("aria-expanded") == "false") {
        var date = new Date();
        $('#sigDateTime').val(new Date(date.getTime() - (date.getTimezoneOffset() * 60000)).toJSON().slice(0, 19));
    }

}
// function testmultiple() {
//     // var d = new Date("06-07-2015 7:06:00");
//     // console.log(d.toLocaleString());
//     // var d = new Date("06-07-2015 7:06:00");
//     console.log(new Date("06-07-2015 7:06:00").toLocaleString());
// }

function clearSightings() {
    $('#sigContainer').empty();
}

function loadSightings() {

    var sigContainer = $('#sigContainer');

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/SuperSightings/sightings',
        success: function (data, status) {
            // var sigContainer = $('#sigContainer');
            var sigCount = 1;

            $.each(data, function (index, sighting) {

                var sigId = sighting.sightingId;
                var sigEnt = sighting.entities;
                var sigLoc = sighting.location;
                var sigDateTime = sighting.sightingDateTime;

                var sigLocation = sigLoc.locationName;
                sigDateTime = new Date(sigDateTime).toLocaleString();

                var sigHeroContainer = $('<ul>')
                    .attr({ id: 'sigHeroes' + sigCount, class: 'list-inline text-white', style: 'margin-left: 5%' })
                    .append($('<input>')
                        .attr({ type: 'hidden', id: 'sigHeroIdValue' + sigCount })
                        .val(sigId))

                var heroCounter = 0;
                for (var i = 0; i < sigEnt.length; i++) {
                    if (sigEnt[i].isHero) {
                        heroCounter++;
                        sigHeroContainer
                            .append($('<li>')
                                .attr({ id: 'sig' + sigCount + 'Hero' + i, val: sigEnt[i].entityId, class: 'list-inline-item', style: 'margin-right: 3%;' })
                                .text(sigEnt[i].entityName))
                    }
                }

                if (heroCounter == 0) {
                    sigHeroContainer
                        .append($('<li>')
                            .attr({ class: 'list-inline-item', style: 'margin-right: 3%;' })
                            .text("No Superheroes sighted."))
                }

                var sigVillainContainer = $('<ul>')
                    .attr({ id: 'sigVillains' + sigCount, class: 'list-inline text-white', style: 'margin-left: 5%' })
                    .append($('<input>')
                        .attr({ type: 'hidden', id: 'sigVillainIdValue' + sigCount })
                        .val(sigId))

                var villainCounter = 0;
                for (var i = 0; i < sigEnt.length; i++) {
                    if (!sigEnt[i].isHero) {
                        villainCounter++;
                        sigVillainContainer
                            .append($('<li>')
                                .attr({ id: 'sig' + sigCount + 'Hero' + i, val: sigEnt[i].entityId, class: 'list-inline-item', style: 'margin-right: 3%;' })
                                .text(sigEnt[i].entityName))
                    }
                }

                if (villainCounter == 0) {
                    sigVillainContainer
                        .append($('<li>')
                            .attr({ class: 'list-inline-item', style: 'margin-right: 3%;' })
                            .text("No Supervillains sighted."))
                }

                var sightingItem = $('<div>')
                    .attr({ id: 'sigId' + sigCount, class: 'container' })
                    .append($('<input>')
                        .attr({ type: 'hidden', id: 'sigIdValue' + sigCount })
                        .val(sigId))
                    .append($('<div>')
                        .attr({ id: 'sigNavTog' + sigCount, class: 'pos-f-t', style: 'padding-bottom: 0.35em;' })
                        .append($('<nav>')
                            .attr({ class: 'navbar navbar-dark bg-secondary' })
                            .append($('<div>')
                                .attr({ class: 'row' })
                                .append($('<div>')
                                    .attr({ class: 'col-1' })
                                    .append($('<button>')
                                        .attr({ class: 'navbar-toggler', type: 'button', "data-toggle": 'collapse' })
                                        .attr({ "data-target": '#navbarTogEx' + sigCount, "aria-controls": "navbarTogEx" + sigCount })
                                        .attr({ "aria-expanded": 'false', "aria-label": 'Toggle navigation' })
                                        .append($('<span>')
                                            .attr({ class: 'navbar-toggler-icon' }))))
                                .append($('<div>')
                                    .attr({ class: 'col-11', style: 'padding-top: 0.75%;' })
                                    .append($('<h6>')
                                        .attr({ id: 'sigName' + sigCount, class: 'text-white', style: 'display: inline-block; margin-left: 1em; width: 50ch;' })
                                        .text(sigLocation))
                                    .append($('<span>')
                                        .attr({ class: 'text-white', style: 'font-size: 90%;' })
                                        .text(sigDateTime)
                                    )
                                )))
                        .append($('<div>')
                            .attr({ id: 'navbarTogEx' + sigCount, class: 'collapse' })
                            .append($('<div>')
                                .attr({ class: 'bg-dark p-4' })
                                .append($('<h6>')
                                    .attr({ class: 'text-white', style: 'text-decoration: underline; margin-left: 5%; margin-bottom: 0.25%;' })
                                    .text('Superheroes'))
                                .append(sigHeroContainer)
                                .append($('<h6>')
                                    .attr({ class: 'text-white', style: 'text-decoration: underline; margin-left: 5%; margin-bottom: 0.25%;' })
                                    .text('Supervillains'))
                                .append(sigVillainContainer)
                                .append($('<form>')
                                    .attr({ style: 'text-align: center; padding-top: 2%;' })
                                    .append($('<div>')
                                        .attr({ class: 'form-group' })
                                        .append($('<button>')
                                            .attr({ type: 'button', class: 'btn btn-sm btn-outline-warning', style: 'width: 45%; margin-right: 2%;', onclick: 'editSighting(' + sigId + ')' })
                                            .text('Edit'))
                                        .append($('<button>')
                                            .attr({ type: 'button', class: 'btn btn-sm btn-outline-danger', style: 'width: 45%; margin-left: 2%;', onclick: 'deleteSighting(' + sigId + ')' })
                                            .text('Delete')))))))

                sigContainer.append(sightingItem);
                sigCount++;

            });

        },
        error: function () {
            sigContainer
                .attr({ class: 'container' })
                .append($('<ul>')
                    .attr({ class: 'list-group' })
                    .append($('<li>')
                        .attr({ class: 'list-group-item list-group-item-danger', style: 'width: 92.5%, margin: auto;' })
                        .text('Error calling SuperSightings web service.  Please try again later.')))
        }
    });
}

function deleteSighting(sightingId) {
    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8080/SuperSightings/sighting/' + sightingId,
        success: function (status) {
            clearSightings();
            loadSightings();
        }
    });
}

function editSighting(sightingId) {

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/SuperSightings/sighting/' + sightingId,
        success: function (data, status) {
            var sigEnts = data.entities;
            var sigLoc = data.location;
            $('#editSigId').val(data.sightingId);
            var sigDat = new Date(data.sightingDateTime).toJSON().slice(0,19);

            $('#editSigLoc').val(sigLoc.locationId);
            $('#editSigDateTime').val(sigDat);
            
            var selectEntArray = [];

            for (var i = 0; i < sigEnts.length; i++) {
                selectEntArray[i] = sigEnts[i].entityId;
            }

            $('#editSigEnts').val(selectEntArray);
        },
        error: function () {

        }

    });

    $('#fadeSigMainDiv').fadeTo("slow", 0.3);
    document.getElementById('fadeSigMainDiv').style.pointerEvents = "none";
    // .setAttribute( "pointer-events", 'none' );
    $('#editSigDiv').fadeTo("slow", 1);
}

function clearEditErrors() {
    $('#editSigErrors').empty();
    $('#editSigErrors').hide();
}

function cancelEditSig() {
    $('#editSigDiv').fadeOut("slow", "linear", function () {
        document.getElementById('editSigForm').classList.remove('was-validated');
        clearEditErrors()
        $('#editSigName').val('');
        $('#editSigDescription').val('');
        $('#editSigAddress').val('');
        $('#editSigLatitude').val('');
        $('#editSigLongitude').val('');
    });
    $('#fadeSigMainDiv').fadeTo("slow", 1);
    document.getElementById('fadeSigMainDiv').style.pointerEvents = "auto";
}

function clearAddErrors() {
    $('#addSigErrors').empty();
    $('#addSigErrors').hide();
}

function cancelAddSig() {
    document.getElementById('addSigForm').classList.remove('was-validated');
    clearAddErrors()
    // document.getElementById('addSigNavButton').setAttribute( "aria-expanded", 'false' );
    // document.getElementById('addSigNavButton').setAttribute( "class", 'navbar-toggler collapsed' );
    $('#selectSigEntities').val('');
    $('#selectSigLocation').val('');

    $('#fadeSigMainDiv').fadeTo("slow", 1);
}

function loadEntities() {

    var sigEntSelect = $('#selectSigEntities');
    var sigEditEntSelect = $('#editSigEnts');

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/SuperSightings/entities',
        success: function (data, status) {
            // var entContainer = $('#entContainer');
            var entCount = 1;

            $.each(data, function (index, entity) {

                var entId = entity.entityId;
                var entName = entity.entityName;
                // var entDesc = entity.entityDescription;
                // var entPowers = entity.powers;
                // var entIsHero = entity.isHero;
                // var entOrganizations = entity.organizations;

                sigEntSelect
                    .append($('<option>')
                        .attr({ id: 'sigSelectEnt' + entCount, value: entId, class: 'text-white', style: 'font-size: 80%;' })
                        .text(entName))

                sigEditEntSelect
                    .append($('<option>')
                        .attr({ id: 'sigSelectEditEnt' + entCount, value: entId, class: 'text-white', style: 'font-size: 80%;' })
                        .text(entName))

                entCount++;

            });

        },
        error: function () {
            entContainer
                .attr({ class: 'container' })
                .append($('<ul>')
                    .attr({ class: 'list-group' })
                    .append($('<li>')
                        .attr({ class: 'list-group-item list-group-item-danger', style: 'width: 92.5%, margin: auto;' })
                        .text('Error calling SuperSightings web service.  Please try again later.')))
        }
    });
}

function loadLocations() {

    var sigLocSelect = $('#selectSigLocation');
    var sigEditLocSelect = $('#editSigLoc');

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/SuperSightings/locations',
        success: function (data, status) {
            // var locContainer = $('#locContainer');
            var locCount = 1;

            $.each(data, function (index, location) {

                var locId = location.locationId;
                var locName = location.locationName;

                sigLocSelect
                    .append($('<option>')
                        .attr({ id: 'sigSelectLoc' + locCount, value: locId, class: 'text-white', style: 'font-size: 80%;' })
                        .text(locName))

                sigEditLocSelect
                    .append($('<option>')
                        .attr({ id: 'sigSelectEditLoc' + locCount, value: locId, class: 'text-white', style: 'font-size: 80%;' })
                        .text(locName))

                locCount++;

            });

        },
        error: function () {
            locContainer
                .attr({ class: 'container' })
                .append($('<ul>')
                    .attr({ class: 'list-group' })
                    .append($('<li>')
                        .attr({ class: 'list-group-item list-group-item-danger', style: 'width: 92.5%, margin: auto;' })
                        .text('Error calling SuperSightings web service.  Please try again later.')))
        }
    });
}

function formValidate(form) {
    form.classList.add('was-validated');
    if (form.checkValidity() === false) {
        // event.preventDefault();
        // event.stopPropagation();
        return true;
    } else {
        return false;
    }

}