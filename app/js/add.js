/**
 * Created by akash on 6/21/16.
 */
(function (window) {

    var Controls = {
        addFriendList: document.getElementById('add-friend-list'),
        addFriend: document.getElementById('add-friend'),
        destInput: {
            name: document.getElementById('to-input'),
            source: document.getElementById('from')
        },
        form: {
            name: document.getElementById('to-input-form'),
            source: document.getElementById('from-form')
        },
        destTmpl: document.getElementById('destination-template'),
        destWrapper: document.getElementById('destination-wrapper')
    };

    Controls.addFriendList.addEventListener('click', addFriendList);
    Controls.addFriend.addEventListener('click', addFriend);

    if (typeof axios === 'undefined') {
        throw 'axios not defined'
    }

    if (typeof toastr === 'undefined') {
        throw 'notifications wont work'
    }

    var API_URL = "http://localhost:8080";

    var friends = [];

    function addFriend(event) {
        event.preventDefault();

        if (!Validations.dest()) {
            return;
        }

        var details = getFriend();
        friends.push(details);

        displayFriend(details);
    }

    function addFriendList(event) {
        event.preventDefault();

        if (!Validations.route()) {
            return;
        }

        var route = getRouteDetails();

        axios.post(API_URL + "/add/friend", route)
            .then(Validations.addSuccess)
            .catch(Validations.addFailure);

    }

    var Validations = {};

    Validations.dest= function () {
        if (Controls.destInput.name.value == '' || Controls.destInput.name.value == null) {
            Controls.form.name.classList.add('has-error');
            toastr.error("You forgot name of friend", "Input Error");
            return false;
        } else {
            Controls.form.name.classList.remove('has-error');
            Controls.form.name.classList.add('has-success');
        }

        return true;
    };

    Validations.route = function () {
        if (Controls.destInput.source.value == '' ||
            Controls.destInput.source.value == null) {
            Controls.form.source.classList.remove('has-success');
            Controls.form.source.classList.add('has-error');
            toastr.error("You forgot your name", "Input Error");
            return false;
        } else {
            Controls.form.source.classList.remove('has-error');
            Controls.form.source.classList.add('has-success');
        }

        if (friends.length <= 0) {
            toastr.error("You forgot to add friends", "Input Error");
            return false;
        }

        return true;
    };

    Validations.addSuccess = function (response) {
        console.log(response);
        toastr.success("Route Added", "Success");

        friends = [];

        Controls.destWrapper.innerHTML = "";
        Controls.destInput.name.value = "";
        Controls.destInput.source.value = "";
    };

    Validations.addFailure = function (reason) {
        console.log(reason);
        toastr.error("Could not reach servers", "Error");

        friends = [];
    };

    function getFriend() {
        return Controls.destInput.name.value
    }

    function getRouteDetails() {
        return {
            name: Controls.destInput.source.value,
            friends: friends
        }
    }

    function displayFriend(friendName) {
        var source = Controls.destTmpl.innerHTML;
        var template = Handlebars.compile(source);

        var html = template({
          name: friendName
        });

        var div = document.createElement('div');
        div.innerHTML = html;
        Controls.destWrapper.appendChild(div);

        Controls.destInput.name.value = '';
    }


})();
