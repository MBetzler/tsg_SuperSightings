/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    loadEntities();
    loadPowers();
    loadOrganizations();

    // $('#entNav').offset({ top: $('#entPH').height() });
    // var entNavOffset = $('#entNav').offset();
    // $('#entContainer').offset({ top: entNavOffset.top + $('#entNav').height() });

    // showHeight($('#addEntDescription').height());
    // showHeight(addEntAddress);
    $('#addEntity').click(function (event) {

        clearEntAddErrors();

        if (formValidate($('#addEntForm')[0])) {
            return false;
        }

        // var entContainer = $('#entContainer');

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/SuperSightings/entity',
            data: JSON.stringify({
                entityName: $('#addEntName').val(),
                entityDescription: $('#addEntDescription').val(),
                powerIds: $('#selectEntPowers').val(),
                isHero: $('input[name="entIsHero"]:checked').val(),
                organizationIds: $('#selectEntOrgs').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json',
            success: function (data, status) {
                document.getElementById('addEntForm').classList.remove('was-validated');
                $('#addEntName').val('');
                $('#addEntDescription').val('');
                $('#selectEntPowers').val('');
                // $('#addEntLatitude').val('');
                $('#selectEntOrgs').val('');

                clearEntities();
                loadEntities();
            },
            error: function (errorResponse) {
                $('#addEntErrors').show();
                var response = $.parseJSON(errorResponse.responseText).message;
                var errorArray = new Array();
                errorArray = response.split(',');
                // response = JSON.parse(response.responseText).message;
                for (var i = 0; i < errorArray.length; i++) {
                    $('#addEntErrors')
                        .append($('<p>')
                            .attr({ class: 'small', style: 'color: red; padding: 0; margin-top: 0; margin-bottom: 0;' })
                            // .text('Error calling web service.  Please try again later.'));
                            .text(errorArray[i]));
                }
                // $('#addEntErrors').show();
                // entContainer
                //     .attr({ class: 'container' })
                //     .append($('<ul>')
                //         .attr({ class: 'list-group' })
                //         .append($('<li>')
                //             .attr({ class: 'list-group-item list-group-item-danger', style: 'width: 92.5%, margin: auto;' })
                //             .text('Error calling SuperSightings web service.  Please try again later.')));
            }

        });
    });

    $('#saveEntEdit').click(function (event) {

        clearEntEditErrors();

        if (formValidate($('#editEntForm')[0])) {
            return false;
        }

        // var entContainer = $('#entContainer');

        $.ajax({
            type: 'PUT',
            url: 'http://localhost:8080/SuperSightings/entity/' + $('#editEntId').val(),
            data: JSON.stringify({
                entityId: $('#editEntId').val(),
                entityName: $('#editEntName').val(),
                entityDescription: $('#editEntDescription').val(),
                powerIds: $('#editEntPowers').val(),
                isHero: $('input[name="editEntIsHero"]:checked').val(),
                organizationIds: $('#editEntOrgs').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json',
            success: function (data, status) {
                document.getElementById('editEntForm').classList.remove('was-validated');
                $('#editEntDiv').fadeOut("slow", "linear", function () {
                    // document.getElementById('editEntForm').classList.remove('was-validated');
                    $('#addEntName').val('');
                    $('#addEntDescription').val('');
                    $('#selectEntPowers').val('');
                    // $('#addEntLatitude').val('');
                    $('#selectEntOrgs').val('');
                });

                $('#fadeEntMainDiv').fadeTo("fast", 0, function () {
                    clearEntities();
                    loadEntities();
                    document.getElementById('fadeEntMainDiv').style.pointerEvents = "auto";
                });

                $('#fadeEntMainDiv').fadeTo("slow", 1);

            },
            error: function (errorResponse) {
                $('#editEntErrors').show();
                var response = $.parseJSON(errorResponse.responseText).message;
                var errorArray = new Array();
                errorArray = response.split(',');
                // response = JSON.parse(response.responseText).message;
                for (var i = 0; i < errorArray.length; i++) {
                    $('#editEntErrors')
                        .append($('<p>')
                            .attr({ class: 'small', style: 'color: red; padding: 0; margin-top: 0; margin-bottom: 0;' })
                            // .text('Error calling web service.  Please try again later.'));
                            .text(errorArray[i]));
                }


                // entContainer
                //     .attr({ class: 'container' })
                //     .append($('<ul>')
                //         .attr({ class: 'list-group' })
                //         .append($('<li>')
                //             .attr({ class: 'list-group-item list-group-item-danger', style: 'width: 92.5%, margin: auto;' })
                //             .text('Error calling SuperSightings web service.  Please try again later.')));
            }

        });
    });


    $('#addPower').click(function (event) {

        clearAddPowErrors();

        if (formValidate($('#addPowForm')[0])) {
            return false;
        }

        // var entContainer = $('#entContainer');

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/SuperSightings/power',
            data: JSON.stringify({
                powerDescription: $('#addPowDescription').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json',
            success: function (data, status) {
                document.getElementById('addPowForm').classList.remove('was-validated');
                $('#addPowDescription').val('');

                clearPowers();
                loadPowers();
            },
            error: function (errorResponse) {
                $('#addPowErrors').show();
                var response = $.parseJSON(errorResponse.responseText).message;
                var errorArray = new Array();
                errorArray = response.split(',');
                // response = JSON.parse(response.responseText).message;
                for (var i = 0; i < errorArray.length; i++) {
                    $('#addPowErrors')
                        .append($('<p>')
                            .attr({ class: 'small', style: 'color: red; padding: 0; margin-top: 0; margin-bottom: 0;' })
                            // .text('Error calling web service.  Please try again later.'));
                            .text(errorArray[i]));
                }
                // $('#addEntErrors').show();
                // entContainer
                //     .attr({ class: 'container' })
                //     .append($('<ul>')
                //         .attr({ class: 'list-group' })
                //         .append($('<li>')
                //             .attr({ class: 'list-group-item list-group-item-danger', style: 'width: 92.5%, margin: auto;' })
                //             .text('Error calling SuperSightings web service.  Please try again later.')));
            }

        });
    });

    $('#savePowEdit').click(function (event) {

        clearPowEditErrors();

        if (formValidate($('#editPowForm')[0])) {
            return false;
        }

        // var entContainer = $('#entContainer');

        $.ajax({
            type: 'PUT',
            url: 'http://localhost:8080/SuperSightings/power/' + $('#editPowId').val(),
            data: JSON.stringify({
                powerId: $('#editPowId').val(),
                powerDescription: $('#editPowDescription').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json',
            success: function (data, status) {
                document.getElementById('editPowForm').classList.remove('was-validated');
                $('#editPowDiv').fadeOut("slow", "linear", function () {
                    // document.getElementById('editEntForm').classList.remove('was-validated');
                    $('#editPowDescription').val('');
                });

                $('#fadeEntMainDiv').fadeTo("fast", 0, function () {
                    clearPowers();
                    loadPowers();
                    document.getElementById('fadeEntMainDiv').style.pointerEvents = "auto";
                });

                $('#fadeEntMainDiv').fadeTo("slow", 1);

            },
            error: function (errorResponse) {
                $('#editPowErrors').show();
                var response = $.parseJSON(errorResponse.responseText).message;
                var errorArray = new Array();
                errorArray = response.split(',');
                // response = JSON.parse(response.responseText).message;
                for (var i = 0; i < errorArray.length; i++) {
                    $('#editPowErrors')
                        .append($('<p>')
                            .attr({ class: 'small', style: 'color: red; padding: 0; margin-top: 0; margin-bottom: 0;' })
                            // .text('Error calling web service.  Please try again later.'));
                            .text(errorArray[i]));
                }


                // entContainer
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
// testmultiple($('[id^=ent' + 2 + 'Power]').toArray());
// testmultiple($('[id^=ent' + 2 + 'Power]').toArray());
// function testmultiple() {
//     // var tableVal = [];
//     var temp = $('[id^=ent' + 2 + 'Power]').toArray()
//     for (var i = 0; i < temp.length; i++) {
//         console.log($(this).attr('value'));
//     }
//     // console.log(tableVal.length);
//     // console.log(tableVal[1]);
//     // console.log($('[id^=ent' + 2 + 'Power]').toArray());
//     // var testarray = [];
//     // testarray = $('[id^=ent' + 2 + 'Power]').toArray();
//     // console.log("console: " + testarray);

//     // for (var i = 0; i < stuff[].length; i++) {
//     //     console.log($('[id^=ent' + 2 + 'Power]').toArray()[i].val);
//     // }

// }



// function testmultiple() {
//     console.log($('input[name="entIsHero"]:checked').val());
// }

function clearEntities() {
    $('#entContainer').empty();
}

function loadEntities() {

    var entContainer = $('#entContainer');

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/SuperSightings/entities',
        success: function (data, status) {
            // var entContainer = $('#entContainer');
            var entCount = 1;

            $.each(data, function (index, entity) {

                var entId = entity.entityId;
                var entName = entity.entityName;
                var entDesc = entity.entityDescription;
                var entPowers = entity.powers;
                var entIsHero = entity.isHero;
                var entOrganizations = entity.organizations;


                var entPowerContainer = $('<ul>')
                    .attr({ id: 'entPowers' + entCount, class: 'list-inline text-white', style: 'margin-left: 5%;' })
                    .append($('<input>')
                        .attr({ type: 'hidden', id: 'entPowerIdValue' + entCount })
                        .val(entId))

                for (var i = 0; i < entPowers.length; i++) {
                    entPowerContainer
                        .append($('<li>')
                            .attr({ id: 'ent' + entCount + 'Power' + i, val: entPowers[i].powerId, class: 'list-inline-item', style: 'margin-right: 3%;' })
                            .text(entPowers[i].powerDescription))
                }

                var entOrgContainer = $('<ul>')
                    .attr({ id: 'entOrgs' + entCount, class: 'list-inline text-white', style: 'margin-left: 5%;' })
                    .append($('<input>')
                        .attr({ type: 'hidden', id: 'entOrgIdValue' + entCount })
                        .val(entId))

                for (var i = 0; i < entOrganizations.length; i++) {
                    entOrgContainer
                        .append($('<li>')
                            .attr({ id: 'ent' + entCount + 'Org' + i, val: entOrganizations[i].organizationId, class: 'list-inline-item', style: 'margin-right: 3%;' })
                            .text(entOrganizations[i].organizationName))
                }

                var entImageSource;
                if (entIsHero) {
                    entImageSource = '../images/Hero.png';
                } else {
                    entImageSource = '../images/Villain.png';
                }

                var entityItem = $('<div>')
                    .attr({ id: 'entId' + entCount, class: 'container' })
                    .append($('<input>')
                        .attr({ type: 'hidden', id: 'entIdValue' + entCount })
                        .val(entId))
                    .append($('<div>')
                        .attr({ id: 'entNavTog' + entCount, class: 'pos-f-t', style: 'padding-bottom: 0.35em;' })
                        .append($('<nav>')
                            .attr({ class: 'navbar navbar-dark bg-secondary' })
                            .append($('<div>')
                                .attr({ class: 'row' })
                                .append($('<div>')
                                    .attr({ class: 'col-1' })
                                    .append($('<button>')
                                        .attr({ class: 'navbar-toggler', type: 'button', "data-toggle": 'collapse' })
                                        .attr({ "data-target": '#navbarTogEx' + entCount, "aria-controls": "navbarTogEx" + entCount })
                                        .attr({ "aria-expanded": 'false', "aria-label": 'Toggle navigation' })
                                        .append($('<span>')
                                            .attr({ class: 'navbar-toggler-icon' }))))
                                .append($('<div>')
                                    .attr({ class: 'col-11', style: 'padding-top: 0.75%;' })
                                    .append($('<h6>')
                                        .attr({ id: 'entName' + entCount, class: 'text-white', style: 'display: inline-block; margin-left: 2em; width: 50ch;' })
                                        .text(entName))
                                    .append($('<span>')
                                        .attr({ id: 'entIsHero' + entCount, val: entIsHero })
                                        .append($('<img>')
                                            .attr({ src: entImageSource, style: 'width: 2em;' })))
                                )))
                        .append($('<div>')
                            .attr({ id: 'navbarTogEx' + entCount, class: 'collapse' })
                            .append($('<div>')
                                .attr({ class: 'bg-dark p-4' })
                                .append($('<p>')
                                    .attr({ id: 'entDesc' + entCount, class: 'text-white', style: 'margin-left: 2%; margin-right: 2%;' })
                                    .text(entDesc))
                                .append($('<h6>')
                                    .attr({ class: 'text-white', style: 'text-decoration: underline; margin-left: 5%; margin-bottom: 0.25%;' })
                                    .text('Powers'))
                                .append(entPowerContainer)
                                .append($('<h6>')
                                    .attr({ class: 'text-white', style: 'text-decoration: underline; margin-left: 5%; margin-bottom: 0.25%;' })
                                    .text('Affiliations'))
                                .append(entOrgContainer)
                                .append($('<form>')
                                    .attr({ style: 'text-align: center; padding-top: 2%;' })
                                    .append($('<div>')
                                        .attr({ class: 'form-group' })
                                        .append($('<button>')
                                            .attr({ type: 'button', class: 'btn btn-sm btn-outline-warning', style: 'width: 45%; margin-right: 2%;', onclick: 'editEntity(' + entId + ')' })
                                            .text('Edit'))
                                        .append($('<button>')
                                            .attr({ type: 'button', class: 'btn btn-sm btn-outline-danger', style: 'width: 45%; margin-left: 2%;', onclick: 'deleteEntity(' + entId + ')' })
                                            .text('Delete')))))))

                entContainer.append(entityItem);
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

function deleteEntity(entityId) {
    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8080/SuperSightings/entity/' + entityId,
        success: function (status) {
            clearEntities();
            loadEntities();
        }
    });
}

function editEntity(entityId) {

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/SuperSightings/entity/' + entityId,
        success: function (data, status) {
            $('#editEntName').val(data.entityName);
            $('#editEntDescription').val(data.entityDescription);
            var entPowers = data.powers;
            $('#editEntIsHero').val(data.isHero);
            var entOrgs = data.organizations;
            $('#editEntId').val(data.entityId);

            var selectPowerArray = [];
            var selectOrgArray = [];

            for (var i = 0; i < entPowers.length; i++) {
                selectPowerArray[i] = entPowers[i].powerId;
            }

            for (var i = 0; i < entOrgs.length; i++) {
                selectOrgArray[i] = entOrgs[i].organizationId;
            }

            $('#editEntPowers').val(selectPowerArray);
            $('#editEntOrgs').val(selectOrgArray);
        },

        error: function () {

        }

    });

    $('#fadeEntMainDiv').fadeTo("slow", 0.3);
    document.getElementById('fadeEntMainDiv').style.pointerEvents = "none";
    // .setAttribute( "pointer-events", 'none' );
    $('#editEntDiv').fadeTo("slow", 1);
}

function clearEntEditErrors() {
    $('#editEntErrors').empty();
    $('#editEntErrors').hide();
}

function cancelEditEnt() {
    $('#editEntDiv').fadeOut("slow", "linear", function () {
        document.getElementById('editEntForm').classList.remove('was-validated');
        clearEntEditErrors()
        $('#editEntName').val('');
        $('#editEntDescription').val('');
        $('#editEntPowers').val('');
        $('#editEntOrgs').val('');
        // $('#editEntLongitude').val('');
    });
    $('#fadeEntMainDiv').fadeTo("slow", 1);
    document.getElementById('fadeEntMainDiv').style.pointerEvents = "auto";
}

function clearEntAddErrors() {
    $('#addEntErrors').empty();
    $('#addEntErrors').hide();
}

function cancelAddEnt() {
    document.getElementById('addEntForm').classList.remove('was-validated');
    clearEntAddErrors()
    // document.getElementById('addEntNavButton').setAttribute( "aria-expanded", 'false' );
    // document.getElementById('addEntNavButton').setAttribute( "class", 'navbar-toggler collapsed' );
    $('#addEntName').val('');
    $('#addEntDescription').val('');
    $('#selectEntPowers').val('');
    // $('#addEntLatitude').val('');
    $('#selectEntOrgs').val('');

    $('#fadeEntMainDiv').fadeTo("slow", 1);
}




function loadPowers() {

    var powContainer = $('#powContainer');
    var entPowerSelect = $('#selectEntPowers');
    var entEditPowerSelect = $('#editEntPowers');

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/SuperSightings/powers',
        success: function (data, status) {
            // var entContainer = $('#entContainer');
            var powCount = 1;

            $.each(data, function (index, power) {

                var powId = power.powerId;
                var powDesc = power.powerDescription;

                var powerItem = $('<div>')
                    .attr({ class: 'form-row' })
                    .append($('<div>')
                        .attr({ class: 'col-8' })
                        .append($('<h6>')
                            .attr({ id: 'powDesc' + powCount, class: 'text-white', style: 'font-size: 90%;' })
                            .text(powDesc)))
                    .append($('<div>')
                        .attr({ class: 'col-2' })
                        .append($('<button>')
                            .attr({ type: 'button', class: 'btn btn-sm btn-outline-warning', style: 'width: 100%; margin-left: 1%; margin-right: 2%;', onclick: 'editPower(' + powId + ')' })
                            .text('Edit')))
                    .append($('<div>')
                        .attr({ class: 'col-2' })
                        .append($('<button>')
                            .attr({ type: 'button', class: 'btn btn-sm btn-outline-danger', style: 'width: 100%; margin-left: 1%;', onclick: 'deletePower(' + powId + ')' })
                            .text('Delete')))


                powContainer.append(powerItem);

                entPowerSelect
                    .append($('<option>')
                        .attr({ id: 'entSelectPow' + powCount, value: powId, class: 'text-white', style: 'font-size: 80%;' })
                        .text(powDesc))

                entEditPowerSelect
                    .append($('<option>')
                        .attr({ id: 'entSelectEditPow' + powCount, value: powId, class: 'text-white', style: 'font-size: 80%;' })
                        .text(powDesc))

                powCount++;

            });

        },
        error: function () {
            powContainer
                .attr({ class: 'container' })
                .append($('<ul>')
                    .attr({ class: 'list-group' })
                    .append($('<li>')
                        .attr({ class: 'list-group-item list-group-item-danger', style: 'width: 92.5%, margin: auto;' })
                        .text('Error calling SuperSightings web service.  Please try again later.')))
        }
    });
}




function clearAddPowErrors() {
    $('#addPowErrors').empty();
    $('#addPowErrors').hide();
}

function cancelAddPow() {
    document.getElementById('addPowForm').classList.remove('was-validated');
    clearAddPowErrors()
    // document.getElementById('addEntNavButton').setAttribute( "aria-expanded", 'false' );
    // document.getElementById('addEntNavButton').setAttribute( "class", 'navbar-toggler collapsed' );
    $('#addPowDescription').val('');

    // $('#fadePowMainDiv').fadeTo("slow", 1);
}

function deletePower(powerId) {
    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8080/SuperSightings/power/' + powerId,
        success: function (status) {
            clearPowers();
            loadPowers();
        }
    });
}

function clearPowers() {
    $('#powContainer').empty();
    $('#selectEntPowers').empty();
}


function editPower(powerId) {

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/SuperSightings/power/' + powerId,
        success: function (data, status) {
            $('#editPowDescription').val(data.powerDescription);
            $('#editPowId').val(data.powerId);
        },
        error: function () {

        }

    });

    $('#fadeEntMainDiv').fadeTo("slow", 0.3);
    document.getElementById('fadeEntMainDiv').style.pointerEvents = "none";
    // .setAttribute( "pointer-events", 'none' );
    $('#editPowDiv').fadeTo("slow", 1);
}

function clearPowEditErrors() {
    $('#editPowErrors').empty();
    $('#editPowErrors').hide();
}

function cancelEditPow() {
    $('#editPowDiv').fadeOut("slow", "linear", function () {
        document.getElementById('editPowForm').classList.remove('was-validated');
        clearPowEditErrors()
        $('#editPowDescription').val('');
    });
    $('#fadeEntMainDiv').fadeTo("slow", 1);
    document.getElementById('fadeEntMainDiv').style.pointerEvents = "auto";
}

function loadOrganizations() {

    var entOrgSelect = $('#selectEntOrgs');
    var entEditOrgSelect = $('#editEntOrgs');

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/SuperSightings/organizations',
        success: function (data, status) {
            // var orgContainer = $('#orgContainer');
            var orgCount = 1;

            $.each(data, function (index, organization) {

                var orgId = organization.organizationId;
                var orgName = organization.organizationName;

                entOrgSelect
                    .append($('<option>')
                        .attr({ id: 'entSelectOrg' + orgCount, value: orgId, class: 'text-white', style: 'font-size: 80%;' })
                        .text(orgName))

                entEditOrgSelect
                    .append($('<option>')
                        .attr({ id: 'entSelectEditOrg' + orgCount, value: orgId, class: 'text-white', style: 'font-size: 80%;' })
                        .text(orgName))

                orgCount++;

            });

        },
        error: function () {
            // orgContainer
            //     .attr({ class: 'container' })
            //     .append($('<ul>')
            //         .attr({ class: 'list-group' })
            //         .append($('<li>')
            //             .attr({ class: 'list-group-item list-group-item-danger', style: 'width: 92.5%, margin: auto;' })
            //             .text('Error calling SuperSightings web service.  Please try again later.')))
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