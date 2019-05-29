//UTILS
function getCookie(name) {
    let matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}
var userFromBD;
var roles=[];
function validateEditForm(){
//TODO validation forms
}


// DOCUMENT READY
$(document).ready(function () {
fillMainTable();
$.ajax({
    url:'/api/users/roles',
    method: 'GET',
    beforeSend: function (request) {
        request.setRequestHeader("X-CSRF-TOKEN", getCookie('XSRF-TOKEN'));
    },
    success: function(data){
        $.each(data, function (i, v) {
            roles[v['authority']]=v;
        });
    }
});
});


// AJAX REST FUNCTIONS
function deleteUser(id){
    $.ajax({
        url:'/api/users/'+id,
        method: 'DELETE',
        beforeSend: function(request){
            request.setRequestHeader('X-CSRF-TOKEN', getCookie('XSRF-TOKEN'));
        },
        success: function(data){
            if(data) $('#userRow'+id).remove();
        }
    });
}

function submitCreateForm() {
    let sex;
    $('#createForm [type="radio"]').each(function(){
        if(this.checked){
            sex=$(this).val();
        }
    });
    let userRoles=[];
    $('#createForm').find('option').each(function(i) {
        if (this.selected){
            userRoles.push($(this).val());
        }
    });
    let user={
        'id': $('#idC').val(),
        'login': $('#loginC').val(),
        'password': $('#passwordC').val(),
        'email': $('#emailC').val(),
        'sex': sex,
        'age': $('#ageC').val(),
        'weight': $('#weightC').val(),
        'height': $('#heightC').val(),
        'authorities': userRoles
    };
    let d=JSON.stringify(user);
    $.ajax({
        url:'/api/users/create',
        method: 'POST',
        contentType: "application/json; charset=utf-8",
        data: d,
        beforeSend: function (request) {
            request.setRequestHeader("X-CSRF-TOKEN", getCookie('XSRF-TOKEN'));
            console.log(d);
        },
        success: function () {
                $('[href="#list"]').click();
            $('#mainTable').find('tbody').empty();
                fillMainTable();
        }
    });
}

function submitEditForm() {
    let sex;
    $('#editForm [type="radio"]').each(function(){
        if(this.checked){
            sex=$(this).val();
        }
    });
    let userRoles=[];
    $('#editForm').find('option').each(function(i) {
        if (this.selected){
            userRoles.push(roles[$(this).val()]);
        }
    });
    let user={
        'id': $('#idE').val(),
        'login': $('#loginE').val(),
        'password': $('#passwordE').val(),
        'email': $('#emailE').val(),
        'sex': sex,
        'age': $('#ageE').val(),
        'weight': $('#weightE').val(),
        'height': $('#heightE').val(),
        'authorities': userRoles
    };
    let d=JSON.stringify(user);
    $.ajax({
        url:'/api/users/update',
        method: 'POST',
        contentType: "application/json; charset=utf-8",
        data: d,
        beforeSend: function (request) {
            request.setRequestHeader("X-CSRF-TOKEN", getCookie('XSRF-TOKEN'));
            console.log(d);
        },
        success: function () {
                $('[data-dismiss="modal"]').click();
            $('#mainTable').find('tbody').empty();
                fillMainTable();
        },
        error: function(data){

        }
    });
}

function getUserByName(name) {
    $.ajax({
        url:'/api/users/byLogin/'+name,
        method: 'GET',
        async: false,
        beforeSend: function(request){
            request.setRequestHeader('X-CSRF-TOKEN', getCookie('XSRF-TOKEN'));
        },
        success: function (data) {
            userFromBD=data;
        }
    });
}


// DATA SETTERS
function fillEditModal(id){
    let r=$('#userRow'+id).find('td');
    $('.modal-title').text('Edit user ' + $(r[0]).text());
    $('.modal-body').html('<form id="editForm">' +
        '<div class="form-group">' +
        '<input type="hidden" type="text" name="id" id="idE" value="'+id+'"/>'+
        '<label for="loginE">Login:</label>' +
        '<input class="form-control" type="text" id="loginE" name="login" placeholder="login" required value="'+$(r[0]).text()+'"/>' +

        '<label for="passwordE">Password</label>' +
        '<input class="form-control" type="password" id="passwordE" name="password" placeholder="password"/>'+

        '<label for="emailE">Email:</label>' +
        '<input class="form-control" type="text" id="emailE" name="email" placeholder="email" required value="'+$(r[1]).text()+'"/>' +

        '<label>Sex:</label><br>' +
        '<input type="radio" name="sex" value="true" required '+($(r[2]).text().indexOf('Male')!==-1?'checked':'')+'/> Male ' +
        '<input type="radio" name="sex" value="false" required '+($(r[2]).text().indexOf('Female')!==-1?'checked':'')+'/> Female '+

        '<br><label for="ageE">Age:</label>' +
        '<input class="form-control" type="number" id="ageE" name="age" placeholder="age" required value="'+$(r[3]).text()+'"/>'+

        '<label for="weightE">Weight:</label>' +
        '<input class="form-control" type="number" id="weightE" name="weight" placeholder="weight" step="0.01" required value="'+parseFloat($(r[4]).text()).toFixed(2)+'"/>' +

        '<label for="heightE">Height:</label>' +
        '<input class="form-control" type="number" id="heightE" name="height" placeholder="height" step="0.01" required value="'+parseFloat($(r[4]).text()).toFixed(2)+'"/>'+


        '<label for="roleE">Role:</label>' +
        '<select multiple size="2" class="form-control" name="role" id="roleE" required>' +
        '<option '+($(r[6]).text().indexOf('USER')!==-1?'selected':'')+' value="USER">user</option>' +
        '<option '+($(r[6]).text().indexOf('ADMIN')!==-1?'selected':'')+' value="ADMIN">admin</option>' +
        '</select>'+

        '</div>' +
        '</form>');
    $('.modal-footer').html(
        '<button type="button" class="btn btn-link" data-dismiss="modal">Close </button>' +
        '<button type="button" class="btn btn-primary" oNclick="submitEditForm()">Update user </button>'
    );
}

function fillMainTable() {
    let t=$('#mainTable').find('tbody');
    $.ajax({
        url: "/api/users/all",
        method: 'GET',
        beforeSend: function (request) {
            request.setRequestHeader("X-CSRF-TOKEN", getCookie('XSRF-TOKEN'));
        },
        success: function (data) {
            $.each(data, function (i, v) {
                let userRoles='';
                $(v['authorities']).each(function(){
                    userRoles+=this['authority']+' ';
                });
                t.append('<tr id="userRow'+v['id']+'">' +
                    '<td>'+v['login']+'</td>' +
                    '<td>'+v['email']+'</td>' +
                    '<td>'+(v['sex']?'Male':'Female')+'</td>' +
                    '<td>'+v['age']+'</td>' +
                    '<td>'+parseFloat(v['weight']).toFixed(2)+'</td>' +
                    '<td>'+parseFloat(v['height']).toFixed(2)+'</td>' +
                    '<td>'+userRoles+'</td>' +
                    '<td>'+
                    '<button type="button" class="btn btn-success mr-1"  data-toggle="modal" data-target="#userEditModal" onClick="fillEditModal('+v['id']+')">Edit</button>'+
                    '<button type="button" class="btn btn-danger" onClick="deleteUser('+v['id']+')">Delete</button>'+
                    '</td>' +
                    '</tr>'); //<TR> here
            });
        }
    });
}