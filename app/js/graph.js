/**
 * Created by akash on 6/22/16.
 */
(function () {

    var Controls = {
        input: {
            from: document.getElementById("name"),
            search: document.getElementById("search")
        },
        form: {
            from: document.getElementById('from-form')
        },
        route: {
            routeTemplate: document.getElementById('route-template'),
            pathHolder: document.getElementById('path')
        }
    };

    Controls.input.search.addEventListener('click', getRoute);

    var API_URL = "localhost:8080";

    function getRoute(event) {
        event.preventDefault();

        if (!Validations.query()) {
            return
        }

        var qDetail = View.getInput();

        Model.computeGraph(qDetail);
    }

    var Validations = {
        query: function () {
            if (Controls.input.from.value == "" || Controls.input.from.value == null) {
                Controls.form.from.classList.remove('has-success');
                Controls.form.from.classList.add('has-error');
                return false;
            } else {
                Controls.form.from.classList.add('has-success');
                Controls.form.from.classList.remove('has-error');
            }
            return true;
        }
    };

    var Model = {};

    Model.computeGraph = function (query) {
        axios.get(`http://${API_URL}/friends/${query.name}`)
            .then(Model.graphSuccess)
            .catch(Model.graphFailure);
    };

    Model.graphSuccess = function (response) {
        Model.labelId = createLabelId(response.data.nodes);
        console.log(Model.labelId);
        View.createGraph(response.data);
        View.createFriendLists(response.data);
    };

    Model.graphFailure = function (reason) {
        console.log(reason);
    };

    function createLabelId(nodes) {
        return nodes.reduce((hashMap, cur) => {
                hashMap[cur.id] = cur.label;
                return hashMap;
            }, {});
    }

    var View = {};

    /**
     * Compiles the handlebars template.
     * todo precompile templates http://handlebarsjs.com/precompilation.html
     */
    (function compileTemplate() {
        var source = Controls.route.routeTemplate.innerHTML;
        View.routeTemplate = Handlebars.compile(source);
    })();

    View.getInput = function () {
        return {
            name: Controls.input.from.value
        }
    };

    View.createFriendLists = function (data) {
        var friends = data.nodes;
        View.displayFriendList(friends);
    };

    View.displayFriendList = function (friends) {
          var names = [];
          for (let f of friends) {
            if (names.indexOf(f.label) < 0) {
              names.push(f.label);
            }
            console.log(names);
          }

        var htmlPath = View.routeTemplate({
            friends:names
        });

        var pathNode = document.createElement('div');
        pathNode.innerHTML = htmlPath;
        Controls.route.pathHolder.innerHTML = htmlPath;
    };

    View.createGraph = function (data) {
        // create a network
        var container = document.getElementById('mynetwork');

        // provide the data in the vis format
        var data = {
            nodes: data.nodes,
            edges: data.edges
        };

        // only the options that have shorthand notations are shown.
        var options = {
            nodes:{
                fixed: false,
                font: '12px arial red',
                shadow: true,
                label: "name"
            }
        };

        // initialize your network!
        var network = new vis.Network(container, data, options);
    }
})();
