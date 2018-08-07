/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    loadOrganizations();

    // $('#orgNav').offset({ top: $('#orgPH').height() });
    // var orgNavOffset = $('#orgNav').offset();
    // $('#orgContainer').offset({ top: orgNavOffset.top + $('#orgNav').height() });

    // showHeight($('#addOrgDescription').height());
    // showHeight(addOrgAddress);
    $('#addOrganization').click(function (event) {

        clearAddErrors();

        if (formValidate($('#addOrgForm')[0])) {
            return false;
        }

        var orgContainer = $('#orgContainer');

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/SuperSightings/organization',
            data: JSON.stringify({
                organizationName: $('#addOrgName').val(),
                organizationDescription: $('#addOrgDescription').val(),
                organizationAddress: $('#addOrgAddress').val(),
                organizationEmail: $('#addOrgEmail').val(),
                organizationPhone: $('#addOrgPhone').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json',
            success: function (data, status) {
                document.getElementById('addOrgForm').classList.remove('was-validated');
                $('#addOrgName').val('');
                $('#addOrgDescription').val('');
                $('#addOrgAddress').val('');
                $('#addOrgEmail').val('');
                $('#addOrgPhone').val('');

                clearOrganizations();
                loadOrganizations();
            },
            error: function (errorResponse) {
                $('#addOrgErrors').show();
                var response = $.parseJSON(errorResponse.responseText).message;
                var errorArray = new Array();
                errorArray = response.split(',');
                // response = JSON.parse(response.responseText).message;
                for (var i = 0; i < errorArray.length; i++) {
                    $('#addOrgErrors')
                        .append($('<p>')
                            .attr({ class: 'small', style: 'color: red; padding: 0; margin-top: 0; margin-bottom: 0;' })
                            // .text('Error calling web service.  Please try again later.'));
                            .text(errorArray[i]));
                }
                // // $('#addOrgErrors').show();
                // // orgContainer
                // //     .attr({ class: 'container' })
                // //     .append($('<ul>')
                // //         .attr({ class: 'list-group' })
                // //         .append($('<li>')
                // //             .attr({ class: 'list-group-item list-group-item-danger', style: 'width: 92.5%, margin: auto;' })
                // //             .text('Error calling SuperSightings web service.  Please try again later.')));
            }

        });
    });

    $('#saveOrgEdit').click(function (event) {

        clearEditErrors();

        if (formValidate($('#editOrgForm')[0])) {
            return false;
        }

        var orgContainer = $('#orgContainer');

        $.ajax({
            type: 'PUT',
            url: 'http://localhost:8080/SuperSightings/organization/' + $('#editOrgId').val(),
            data: JSON.stringify({
                organizationId: $('#editOrgId').val(),
                organizationName: $('#editOrgName').val(),
                organizationDescription: $('#editOrgDescription').val(),
                organizationAddress: $('#editOrgAddress').val(),
                organizationEmail: $('#editOrgEmail').val(),
                organizationPhone: $('#editOrgPhone').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json',
            success: function (data, status) {
                document.getElementById('editOrgForm').classList.remove('was-validated');
                $('#editOrgDiv').fadeOut("slow", "linear", function () {
                    // document.getElementById('editOrgForm').classList.remove('was-validated');
                    $('#editOrgName').val('');
                    $('#editOrgDescription').val('');
                    $('#editOrgAddress').val('');
                    $('#editOrgEmail').val('');
                    $('#editOrgPhone').val('');
                });

                $('#fadeOrgMainDiv').fadeTo("fast", 0, function () {
                    clearOrganizations();
                    loadOrganizations();
                    document.getElementById('fadeOrgMainDiv').style.pointerEvents = "auto";
                });

                $('#fadeOrgMainDiv').fadeTo("slow", 1);

            },
            error: function (errorResponse) {
                $('#editOrgErrors').show();
                var response = $.parseJSON(errorResponse.responseText).message;
                var errorArray = new Array();
                errorArray = response.split(',');
                // response = JSON.parse(response.responseText).message;
                for (var i = 0; i < errorArray.length; i++) {
                    $('#editOrgErrors')
                        .append($('<p>')
                            .attr({ class: 'small', style: 'color: red; padding: 0; margin-top: 0; margin-bottom: 0;' })
                            // .text('Error calling web service.  Please try again later.'));
                            .text(errorArray[i]));
                }


                // orgContainer
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

// function showHeight(height) {
//     // $('#testDiv').text("The height for the " + element + " is " + height + "px.");
//     $('#testDiv').text("The height for the is " + height + "px.");
// }

function clearOrganizations() {
    $('#orgContainer').empty();
}

function loadOrganizations() {

    var orgContainer = $('#orgContainer');

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/SuperSightings/organizations',
        success: function (data, status) {
            // var orgContainer = $('#orgContainer');
            var orgCount = 1;

            $.each(data, function (index, organization) {

                var orgId = organization.organizationId;
                var orgName = organization.organizationName;
                var orgDesc = organization.organizationDescription;
                var orgAddress = organization.organizationAddress;
                var orgEmail = organization.organizationEmail;
                var orgPhone = organization.organizationPhone;

                var orgDescLead = orgDesc.substring(0, 20) + " .....";

                var organizationItem = $('<div>')
                    .attr({ id: 'orgId' + orgCount, class: 'container' })
                    .append($('<input>')
                        .attr({ type: 'hidden', id: 'orgIdValue' + orgCount })
                        .val(orgId))
                    .append($('<div>')
                        .attr({ id: 'orgNavTog' + orgCount, class: 'pos-f-t', style: 'padding-bottom: 0.35em;' })
                        .append($('<nav>')
                            .attr({ class: 'navbar navbar-dark bg-secondary' })
                            .append($('<div>')
                                .attr({ class: 'row' })
                                .append($('<div>')
                                    .attr({ class: 'col-1' })
                                    .append($('<button>')
                                        .attr({ class: 'navbar-toggler', type: 'button', "data-toggle": 'collapse' })
                                        .attr({ "data-target": '#navbarTogEx' + orgCount, "aria-controls": "navbarTogEx" + orgCount })
                                        .attr({ "aria-expanded": 'false', "aria-label": 'Toggle navigation' })
                                        .append($('<span>')
                                            .attr({ class: 'navbar-toggler-icon' }))))
                                .append($('<div>')
                                    .attr({ class: 'col-11', style: 'padding-top: 0.75%;' })
                                    .append($('<h6>')
                                        .attr({ id: 'orgName' + orgCount, class: 'text-white', style: 'display: inline-block; margin-left: 1.25em; width:50ch;' })
                                        .text(orgName))
                                    .append($('<span>')
                                        .attr({ class: 'text-white', style: 'font-size: 90%;' })
                                        .text(orgDescLead)))))
                        .append($('<div>')
                            .attr({ id: 'navbarTogEx' + orgCount, class: 'collapse' })
                            .append($('<div>')
                                .attr({ class: 'bg-dark p-4' })
                                .append($('<p>')
                                    .attr({ id: 'orgDesc' + orgCount, class: 'text-white', style: 'margin-left: 2%; margin-right: 2%;' })
                                    .text(orgDesc))
                                .append($('<p>')
                                    .attr({ id: 'orgAddress' + orgCount, class: 'text-white', style: 'margin-left: 2%; margin-right: 2%;' })
                                    .text(orgAddress))
                                .append($('<p>')
                                    .attr({ id: 'orgEmail' + orgCount, class: 'text-white', style: 'margin-left: 2%; margin-right: 2%;' })
                                    .text(orgEmail))
                                    // .html('<span class="text-white;" style="font-size: 300%;">THE EMAIL ADDRESS: </span>' + orgEmail))
                                .append($('<p>')
                                    .attr({ id: 'orgPhone' + orgCount, class: 'text-white', style: 'margin-left: 2%; margin-right: 2%;' })
                                    .text(orgPhone))
                                .append($('<form>')
                                    .attr({ style: 'text-align: center; padding-top: 2%;' })
                                    .append($('<div>')
                                        .attr({ class: 'form-group' })
                                        .append($('<button>')
                                            .attr({ type: 'button', class: 'btn btn-sm btn-outline-warning', style: 'width: 45%; margin-right: 2%;', onclick: 'editOrganization(' + orgId + ')' })
                                            .text('Edit'))
                                        .append($('<button>')
                                            .attr({ type: 'button', class: 'btn btn-sm btn-outline-danger', style: 'width: 45%; margin-left: 2%;', onclick: 'deleteOrganization(' + orgId + ')' })
                                            .text('Delete')))))))

                orgContainer.append(organizationItem);
                orgCount++;

            });

        },
        error: function () {
            orgContainer
                .attr({ class: 'container' })
                .append($('<ul>')
                    .attr({ class: 'list-group' })
                    .append($('<li>')
                        .attr({ class: 'list-group-item list-group-item-danger', style: 'width: 92.5%, margin: auto;' })
                        .text('Error calling SuperSightings web service.  Please try again later.')))
        }
    });
}

function deleteOrganization(organizationId) {
    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8080/SuperSightings/organization/' + organizationId,
        success: function (status) {
            clearOrganizations();
            loadOrganizations();
        }
    });
}

function editOrganization(organizationId) {

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/SuperSightings/organization/' + organizationId,
        success: function (data, status) {
            $('#editOrgName').val(data.organizationName);
            $('#editOrgDescription').val(data.organizationDescription);
            $('#editOrgAddress').val(data.organizationAddress);
            $('#editOrgEmail').val(data.organizationEmail);
            $('#editOrgPhone').val(data.organizationPhone);
            $('#editOrgId').val(data.organizationId);
        },
        error: function () {

        }

    });

    $('#fadeOrgMainDiv').fadeTo("slow", 0.3);
    document.getElementById('fadeOrgMainDiv').style.pointerEvents = "none";
    // .setAttribute( "pointer-events", 'none' );
    $('#editOrgDiv').fadeTo("slow", 1);
}

function clearEditErrors() {
    $('#editOrgErrors').empty();
    $('#editOrgErrors').hide();
}

function cancelEditOrg() {
    $('#editOrgDiv').fadeOut("slow", "linear", function () {
        document.getElementById('editOrgForm').classList.remove('was-validated');
        clearEditErrors()
        $('#editOrgName').val('');
        $('#editOrgDescription').val('');
        $('#editOrgAddress').val('');
        $('#editOrgEmail').val('');
        $('#editOrgPhone').val('');
    });
    $('#fadeOrgMainDiv').fadeTo("slow", 1);
    document.getElementById('fadeOrgMainDiv').style.pointerEvents = "auto";
}

function clearAddErrors() {
    $('#addOrgErrors').empty();
    $('#addOrgErrors').hide();
}

function cancelAddOrg() {
    document.getElementById('addOrgForm').classList.remove('was-validated');
    clearAddErrors()
    // document.getElementById('addOrgNavButton').setAttribute( "aria-expanded", 'false' );
    // document.getElementById('addOrgNavButton').setAttribute( "class", 'navbar-toggler collapsed' );
    $('#addOrgName').val('');
    $('#addOrgDescription').val('');
    $('#addOrgAddress').val('');
    $('#addOrgEmail').val('');
    $('#addOrgPhone').val('');

    // $('#fadeOrgMainDiv').fadeTo("slow", 1);
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


// Junk code for testing - requires uncommenting test button in html
function checkElementVal() {
    console.log("orgEmail text: " + $('#orgEmail1').text());
    console.log("orgEmail val: " + $('#orgEmail1').val());
    console.log("orgEmail html: " + $('#orgEmail1').html());
    // console.log($('#addLocErrors').attr('id'));
    // if ($('#addLocErrors').attr('id') === 'addLocErrors') {
    //     console.log("strictly equal");
    // }
    // console.log("id: " + $('#locErrors1').attr('id'));
    // console.log("substring: " + $('#locErrors1').attr('id').substring(0, 9));

    // console.log("addLocErrors: " + $('#addLocErrors').val());
    // if ($('#addLocErrors').val() != undefined) {
    //     console.log("addLocErrors != undefined is true");
    // }
    // if ($.trim($('#addLocErrors').val())) {
    //     console.log("it evaluated true");
    // }
    // if ($('#addLocErrors').val() != '') {
    //     console.log("addLocErrors != '' is true");
    // }

    // if ($('#addLocErrors').val() != null) {
    //     console.log("addLocErrors != null is true");
    // }
}