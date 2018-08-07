/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    //NEW
    initializeErrorElements()
    loadLocations();

    // $('#locNav').offset({ top: $('#locPH').height() });
    // var locNavOffset = $('#locNav').offset();
    // $('#locContainer').offset({ top: locNavOffset.top + $('#locNav').height() });

    // showHeight($('#addLocDescription').height());
    // showHeight(addLocAddress);
    $('#addLocation').click(function (event) {

        //NEW
        //Use generic clear function instead
        // clearAddErrors();
        clearErrors($('#addLocErrors'));

        if (formValidate($('#addLocForm')[0])) {
            return false;
        }

        // var locContainer = $('#locContainer');

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/SuperSightings/location',
            data: JSON.stringify({
                locationName: $('#addLocName').val(),
                locationDescription: $('#addLocDescription').val(),
                locationAddress: $('#addLocAddress').val(),
                locationLatitude: $('#addLocLatitude').val(),
                locationLongitude: $('#addLocLongitude').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json',
            success: function (data, status) {
                document.getElementById('addLocForm').classList.remove('was-validated');
                $('#addLocName').val('');
                $('#addLocDescription').val('');
                $('#addLocAddress').val('');
                $('#addLocLatitude').val('');
                $('#addLocLongitude').val('');

                clearLocations();
                loadLocations();
            },
            error: function (xhr, statusText, thrownError) {
                //Catch 0 - no response - append error message, 422 - server validation of data - append error message(s), default/unkown - append error message
                // console.log("xhr: " + xhr);
                // console.log("xhr.responseText: " + xhr.responseText);
                // console.log("xhr.status: " + xhr.status);
                // console.log("xhr.statusText: " + xhr.statusText);
                // console.log("statusText: " + statusText);
                // console.log("thrownError: " + thrownError);
                //New code version 2
                appendErrorMessage($('#addLocErrors'), xhr);
                //New code version 2
                //New code
                // var errorArray = new Array();
                // switch (xhr.status) {
                //     case 0:
                //         errorArray[0] = "Error calling SuperSightings web service.  Please try again later.";
                //         break;
                //     case 422:
                //         var response = $.parseJSON(xhr.responseText).message;
                //         errorArray = response.split(',');
                //         break;
                //     default:
                //         errorArray[0] = "An unknown error occurred.  Please contact the Web Administrator.";
                // }
                // appendErrorMessage($('#addLocErrors'), errorArray);
                //New code
                //Original working code
                // $('#addLocErrors').show();
                // var response = $.parseJSON(xhr.responseText).message;
                // var errorArray = new Array();
                // errorArray = response.split(',');
                // // response = JSON.parse(response.responseText).message;
                // for (var i = 0; i < errorArray.length; i++) {
                //     $('#addLocErrors')
                //         .append($('<p>')
                //             .attr({ class: 'small', style: 'color: red; padding: 0; margin-top: 0; margin-bottom: 0;' })
                //             // .text('Error calling web service.  Please try again later.'));
                //             .text(errorArray[i]));
                // }
                //Original working code
                //Original from initial creation/start - never fully coded/tested/etc
                // $('#addLocErrors').show();
                // locContainer
                //     .attr({ class: 'container' })
                //     .append($('<ul>')
                //         .attr({ class: 'list-group' })
                //         .append($('<li>')
                //             .attr({ class: 'list-group-item list-group-item-danger', style: 'width: 92.5%, margin: auto;' })
                //             .text('Error calling SuperSightings web service.  Please try again later.')));
                //Original from initial creation/start - never fully coded/tested/etc
            }

        });
    });

    $('#saveLocEdit').click(function (event) {

        //NEW
        //Use generic clear function instead
        // clearEditErrors();
        clearErrors($('#editLocErrors'));


        if (formValidate($('#editLocForm')[0])) {
            return false;
        }

        // var locContainer = $('#locContainer');

        $.ajax({
            type: 'PUT',
            url: 'http://localhost:8080/SuperSightings/location/' + $('#editLocId').val(),
            data: JSON.stringify({
                locationId: $('#editLocId').val(),
                locationName: $('#editLocName').val(),
                locationDescription: $('#editLocDescription').val(),
                locationAddress: $('#editLocAddress').val(),
                locationLatitude: $('#editLocLatitude').val(),
                locationLongitude: $('#editLocLongitude').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json',
            success: function (data, status) {
                document.getElementById('editLocForm').classList.remove('was-validated');
                $('#editLocDiv').fadeOut("slow", "linear", function () {
                    // document.getElementById('editLocForm').classList.remove('was-validated');
                    $('#editLocId').val('');
                    $('#editLocName').val('');
                    $('#editLocDescription').val('');
                    $('#editLocAddress').val('');
                    $('#editLocLatitude').val('');
                    $('#editLocLongitude').val('');
                });

                $('#fadeLocMainDiv').fadeTo("fast", 0, function () {
                    clearLocations();
                    loadLocations();
                    document.getElementById('fadeLocMainDiv').style.pointerEvents = "auto";
                });

                $('#fadeLocMainDiv').fadeTo("slow", 1);

            },
            error: function (xhr, statusText, thrownError) {
                //Catch 0 - no response - append error message, 422 - server validation of data - append error message(s), 422 - mismatch of ID's - append error message, default/unkown - append error message
                //New code version 2
                appendErrorMessage($('#editLocErrors'), xhr);
                //New code version 2
                //New code
                // var errorArray = new Array();
                // switch (xhr.status) {
                //     case 0:
                //         errorArray[0] = "Error calling SuperSightings web service.  Please try again later.";
                //         break;
                //     case 422:
                //         var response = $.parseJSON(xhr.responseText).message;
                //         errorArray = response.split(',');
                //         break;
                //     default:
                //         errorArray[0] = "An unknown error occurred.  Please contact the Web Administrator.";
                // }
                // appendErrorMessage($('#editLocErrors'), errorArray);
                //New code
                //Original working code
                // $('#editLocErrors').show();
                // //.parseJSON deprecated in v3 - change to JSON.parse
                // var response = $.parseJSON(errorResponse.responseText).message;
                // //Change declaration to = [] instead of new Array()
                // var errorArray = new Array();
                // errorArray = response.split(',');
                // // response = JSON.parse(response.responseText).message;
                // for (var i = 0; i < errorArray.length; i++) {
                //     $('#editLocErrors')
                //         .append($('<p>')
                //             .attr({ class: 'small', style: 'color: red; padding: 0; margin-top: 0; margin-bottom: 0;' })
                //             // .text('Error calling web service.  Please try again later.'));
                //             .text(errorArray[i]));
                // }
                //Original working code


                //Original from initial creation/start - never fully coded/tested/etc
                // locContainer
                //     .attr({ class: 'container' })
                //     .append($('<ul>')
                //         .attr({ class: 'list-group' })
                //         .append($('<li>')
                //             .attr({ class: 'list-group-item list-group-item-danger', style: 'width: 92.5%, margin: auto;' })
                //             .text('Error calling SuperSightings web service.  Please try again later.')));
                //Original from initial creation/start - never fully coded/tested/etc
            }

        });
    });

});

// function showHeight(height) {
//     // $('#testDiv').text("The height for the " + element + " is " + height + "px.");
//     $('#testDiv').text("The height for the is " + height + "px.");
// }

//NEW
function initializeErrorElements() {
    $('#addLocErrors').val('');
    $('#editLocErrors').val('');
}

function clearLocations() {
    $('#locContainer').empty();
}

function loadLocations() {

    var locContainer = $('#locContainer');

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/SuperSightings/locations',
        success: function (data, status) {
            // var locContainer = $('#locContainer');
            if (!$.trim(data)) {
                locContainer
                    .append($('<div>')
                        .attr({ class: 'container' })
                        .append($('<ul>')
                            .attr({ class: 'list-group' })
                            .append($('<li>')
                                .attr({ class: 'list-group-item list-group-item-warning', style: 'width: 92.5%, margin: auto;' })
                                .text('There are no Locations in the database.'))))
            } else {
                // var locCount = 1;

                $.each(data, function (index, location) {

                    var locId = location.locationId;
                    var locName = location.locationName;
                    var locDesc = location.locationDescription;
                    var locAddress = location.locationAddress;
                    var locLat = location.locationLatitude;
                    var locLong = location.locationLongitude;

                    var locationItem = $('<div>')
                        .attr({ id: 'locId' + locId, class: 'container' })
                        // .append($('<input>')
                        //     .attr({ type: 'hidden', id: 'locIdValue' + locCount })
                        //     .val(locId))
                        .append($('<div>')
                            .attr({ id: 'locNavTog' + locId, class: 'pos-f-t', style: 'padding-bottom: 0.35em;' })
                            .append($('<nav>')
                                .attr({ class: 'navbar navbar-dark bg-secondary' })
                                .append($('<div>')
                                    .attr({ class: 'row' })
                                    .append($('<div>')
                                        .attr({ class: 'col-1' })
                                        .append($('<button>')
                                            .attr({ class: 'navbar-toggler', type: 'button', "data-toggle": 'collapse' })
                                            .attr({ "data-target": '#navbarTogEx' + locId, "aria-controls": "navbarTogEx" + locId })
                                            .attr({ "aria-expanded": 'false', "aria-label": 'Toggle navigation' })
                                            .append($('<span>')
                                                .attr({ class: 'navbar-toggler-icon' }))))
                                    .append($('<div>')
                                        .attr({ class: 'col-11', style: 'padding-top: 0.75%;' })
                                        .append($('<h6>')
                                            .attr({ id: 'locName' + locId, class: 'text-white', style: 'display: inline-block; margin-left: 1em; width: 50ch;' })
                                            .text(locName))
                                        .append($('<span>')
                                            .attr({ class: 'text-white', style: 'font-size: 90%;' })
                                            .text("Coordinates: ")
                                            .append($('<span>')
                                                .attr({ id: 'locLat' + locId, style: 'font-size: 90%;' })
                                                .text(locLat))
                                            .append($('<span>')
                                                .attr({ style: 'font-size: 90%;' })
                                                .append('&deg;'))
                                            .append($('<span>')
                                                .text(", "))
                                            .append($('<span>')
                                                .attr({ id: 'locLong' + locId, style: 'font-size: 90%;' })
                                                .text(locLong))
                                            .append($('<span>')
                                                .attr({ style: 'font-size: 90%;' })
                                                .append('&deg;'))))))
                            .append($('<div>')
                                .attr({ id: 'navbarTogEx' + locId, class: 'collapse' })
                                .append($('<div>')
                                    .attr({ class: 'bg-dark p-4' })
                                    .append($('<p>')
                                        .attr({ id: 'locDesc' + locId, class: 'text-white', style: 'margin-left: 2%; margin-right: 2%;' })
                                        .text(locDesc))
                                    .append($('<p>')
                                        .attr({ id: 'locAddress' + locId, class: 'text-white', style: 'margin-left: 2%; margin-right: 2%; margin-bottom: 1%;' })
                                        .text(locAddress))
                                    .append($('<div>')
                                        .attr({ class: 'row', style: 'margin-left: 2%; margin-right: 2%' })
                                        .append($('<div>')
                                            .attr({ id: 'locErrors' + locId, class: 'col', style: 'padding: 0; display: none;' })
                                            .val('')))
                                    .append($('<form>')
                                        .attr({ style: 'text-align: center; padding-top: 3%;' })
                                        .append($('<div>')
                                            .attr({ class: 'form-group' })
                                            .append($('<button>')
                                                .attr({ type: 'button', class: 'btn btn-sm btn-outline-warning', style: 'width: 45%; margin-right: 2%;', onclick: 'editLocation(' + locId + ')' })
                                                .text('Edit'))
                                            .append($('<button>')
                                                .attr({ type: 'button', class: 'btn btn-sm btn-outline-danger', style: 'width: 45%; margin-left: 2%;', onclick: 'deleteLocation(' + locId + ')' })
                                                .text('Delete')))))))

                    locContainer.append(locationItem);
                    // locCount++;

                });
            }

        },
        //--- ORIGINAL ERROR FUNCTION ---
        // error: function () {
        //     locContainer
        //         .attr({ class: 'container' })
        //         .append($('<ul>')
        //             .attr({ class: 'list-group' })
        //             .append($('<li>')
        //                 .attr({ class: 'list-group-item list-group-item-danger', style: 'width: 92.5%, margin: auto;' })
        //                 .text('Error calling SuperSightings web service.  Please try again later.')))
        // }
        error: function (xhr, statusText, thrownError) {
            //Catch 0 - no response - replace with error message, default/unkown - replace with error message
            //I ASSUME...if status code = 0 the data type of xhr must be text, not JSON or otherwise since there was no reponse.  Therefore there is no responseText.
            //These errors will not display if app is actually deployed (NetBeans locally or truly hosted); only work locally when html page(s) are opened directly.
            var errorText = "";
            switch (xhr.status) {
                case 0:
                    errorText = "Error calling SuperSightings web service.  Please try again later.";
                    break;
                default:
                    errorText = "An unknown error occurred.  Please contact the Web Administrator.";
            }

            $('#addLocNavTog').hide();
            locContainer
                .attr({ class: 'container' })
                // .offset({ top: parseInt($('#locNav').css("height")) + parseInt($('#locPH').css("height")) })
                .append($('<ul>')
                    .attr({ class: 'list-group' })
                    .append($('<li>')
                        .attr({ class: 'list-group-item list-group-item-danger', style: 'width: 92.5%, margin: auto;' })
                        .text(errorText)))
        }
    });
}

function deleteLocation(locationId) {

    clearErrors($('#locErrors' + locationId));

    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8080/SuperSightings/location/' + locationId,
        success: function (status) {
            // clearLocations();
            // loadLocations();
            //Remove the element vs an Ajax call; fewer Ajax calls. Even if there is/were a sort order the order is maintained.
            $('#locId' + locationId).remove();
        },

        error: function (xhr, statusText, thrownError) {
            //Catch 0 - no response - append error message, 422 - attempt to delete referenced object - append error message, default/unkown - append error message

            appendErrorMessage($('#locErrors' + locationId), xhr);

            // $('#editLocErrors').show();
            // //.parseJSON deprecated in v3 - change to JSON.parse
            // var response = $.parseJSON(xhr.responseText).message;
            // //Change declaration to = [] instead of new Array()
            // var errorArray = new Array();
            // errorArray = response.split(',');
            // // response = JSON.parse(response.responseText).message;
            // for (var i = 0; i < errorArray.length; i++) {
            //     console.log("response: " + errorArray[i]);
            //     // $('#editLocErrors')
            //     //     .append($('<p>')
            //     //         .attr({ class: 'small', style: 'color: red; padding: 0; margin-top: 0; margin-bottom: 0;' })
            //     //         // .text('Error calling web service.  Please try again later.'));
            //     //         .text(errorArray[i]));
            // }

        }
    });
}

function editLocation(locationId) {

    // $.ajax({
    //     type: 'GET',
    //     url: 'http://localhost:8080/SuperSightings/location/' + locationId,
    //     success: function (data, status) {
    //         $('#editLocName').val(data.locationName);
    //         $('#editLocDescription').val(data.locationDescription);
    //         $('#editLocAddress').val(data.locationAddress);
    //         $('#editLocLatitude').val(data.locationLatitude);
    //         $('#editLocLongitude').val(data.locationLongitude);
    //         $('#editLocId').val(data.locationId);
    //     },
    //     error: function () {
    //         //Catch 0 - no response - replace with error message, default/unkown - replace with error message

    //     }

    // });

    $('#editLocName').val($('#locName' + locationId).text());
    $('#editLocDescription').val($('#locDesc' + locationId).text());
    $('#editLocAddress').val($('#locAddress' + locationId).text());
    $('#editLocLatitude').val($('#locLat' + locationId).text());
    $('#editLocLongitude').val($('#locLong' + locationId).text());
    $('#editLocId').val(locationId);

    $('#fadeLocMainDiv').fadeTo("slow", 0.3);
    document.getElementById('fadeLocMainDiv').style.pointerEvents = "none";
    // .setAttribute( "pointer-events", 'none' );
    $('#editLocDiv').fadeTo("slow", 1);
}

//Replaced by generic function
// function clearEditErrors() {
//     $('#editLocErrors').empty();
//     $('#editLocErrors').hide();
// }

function cancelEditLoc() {
    $('#editLocDiv').fadeOut("slow", "linear", function () {
        document.getElementById('editLocForm').classList.remove('was-validated');

        //NEW
        //Use generic clear function instead
        // clearEditErrors();
        clearErrors($('#editLocErrors'));

        $('#editLocName').val('');
        $('#editLocDescription').val('');
        $('#editLocAddress').val('');
        $('#editLocLatitude').val('');
        $('#editLocLongitude').val('');
    });
    $('#fadeLocMainDiv').fadeTo("slow", 1);
    document.getElementById('fadeLocMainDiv').style.pointerEvents = "auto";
}

//Replaced by generic function
// function clearAddErrors() {
//     $('#addLocErrors').empty();
//     $('#addLocErrors').hide();
// }

function cancelAddLoc() {
    document.getElementById('addLocForm').classList.remove('was-validated');

    //NEW
    //Use generic clear function instead
    // clearAddErrors();
    clearErrors($('#addLocErrors'));

    // document.getElementById('addLocNavButton').setAttribute( "aria-expanded", 'false' );
    // document.getElementById('addLocNavButton').setAttribute( "class", 'navbar-toggler collapsed' );
    $('#addLocName').val('');
    $('#addLocDescription').val('');
    $('#addLocAddress').val('');
    $('#addLocLatitude').val('');
    $('#addLocLongitude').val('');

    // $('#fadeLocMainDiv').fadeTo("slow", 1);
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

// function appendErrorMessage(displayElement, errArray) {
//     displayElement.show();
//     for (var i = 0; i < errArray.length; i++) {
//         displayElement
//             .append($('<p>')
//                 .attr({ class: 'small', style: 'color: red; padding: 0; margin-top: 0; margin-bottom: 0;' })
//                 .text(errArray[i]));
//     }
// }

// function appendErrorMessage(displayElement, errorXhr) {
//     // displayElement.show();
//     var errorArray = new Array();
//     switch (errorXhr.status) {
//         case 0:
//             errorArray[0] = "Error calling SuperSightings web service.  Please try again later.";
//             break;
//         case 422:
//             var response = $.parseJSON(errorXhr.responseText).message;
//             errorArray = response.split(',');
//             break;
//         default:
//             errorArray[0] = "An unknown error occurred.  Please contact the Web Administrator.";
//     }
//     for (var i = 0; i < errorArray.length; i++) {
//         displayElement
//             .append($('<p>')
//                 .attr({ class: 'small', style: 'color: red; padding: 0; margin-top: 0; margin-bottom: 0;' })
//                 .text(errorArray[i]));
//     }
//     displayElement.show();
// }
/////
function appendErrorMessage(displayElement, errorXhr) {
    // displayElement.show();
    var errorTimer;
    var errorArray = new Array();
    switch (errorXhr.status) {
        case 0:
            errorArray[0] = "Error calling SuperSightings web service.  Please try again later.";
            errorTimer = setTimeout(function () {
                clearErrors(displayElement);
            }, 7000);
            displayElement.val(errorTimer);
            break;
        case 422:
            var response = $.parseJSON(errorXhr.responseText).message;
            errorArray = response.split(',');
            //Set Timeout for attempt to Delete a Location resulting in foreign key constraint error.
            if (displayElement.attr('id').substring(0, 9) === 'locErrors') {
                errorTimer = setTimeout(function () {
                    clearErrors(displayElement);
                }, 7000);
                displayElement.val(errorTimer);
            }
            break;
        default:
            errorArray[0] = "An unknown error occurred.  Please contact the Web Administrator.";
            errorTimer = setTimeout(function () {
                clearErrors(displayElement);
            }, 7000);
            displayElement.val(errorTimer);
    }

    for (var i = 0; i < errorArray.length; i++) {
        displayElement
            .append($('<p>')
                .attr({ class: 'small', style: 'color: red; padding: 0; margin-top: 0; margin-bottom: 0;' })
                .text(errorArray[i]));
    }

    displayElement.show();
}

function clearErrors(errorElement) {
    //Cancel Timeout for cases where Cancel or Add is clicked after displaying error, but before Timeout executes
    if (errorElement.val()) {
        clearTimeout(errorElement.val());
        //Clear the Timeout value from the element
        errorElement.val('');
    }

    errorElement.hide();
    errorElement.empty();
}

//Junk code for testing - requires uncommenting test button in html
// function checkElementVal() {
//     console.log($('#addLocErrors').attr('id'));
//     if ($('#addLocErrors').attr('id') === 'addLocErrors') {
//         console.log("strictly equal");
//     }
//     console.log("id: " + $('#locErrors1').attr('id'));
//     console.log("substring: " + $('#locErrors1').attr('id').substring(0, 9));

//     // console.log("addLocErrors: " + $('#addLocErrors').val());
//     // if ($('#addLocErrors').val() != undefined) {
//     //     console.log("addLocErrors != undefined is true");
//     // }
//     // if ($.trim($('#addLocErrors').val())) {
//     //     console.log("it evaluated true");
//     // }
//     // if ($('#addLocErrors').val() != '') {
//     //     console.log("addLocErrors != '' is true");
//     // }

//     // if ($('#addLocErrors').val() != null) {
//     //     console.log("addLocErrors != null is true");
//     // }
// }