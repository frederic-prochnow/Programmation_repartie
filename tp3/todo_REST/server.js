var Hapi = require('hapi');
var Loki = require('lokijs');
var accepts = require('accepts');
var js2xmlparser = require('js2xmlparser');

const pathURI = '/tasks';
var db = new Loki('tasks.json', { autosave: true,
				  autoloadCallback: loadHandler,
				  autoload: true });
var tasks;

var server = new Hapi.Server();
server.connection({ port: 8080});

/*
 * Définition des routes prises en charge par le serveur REST
 */
server.route({
    method: 'GET',
    path: pathURI,
    handler: getTasks
});

server.route({
    method: 'GET',
    path: pathURI + '/{id}',
    handler: getOneTask
});

server.route({
    method: 'GET',
    path: pathURI + '/{id}/nom',
    handler: getTaskName
});

server.route({
    method: 'GET',
    path: pathURI + '/{id}/description',
    handler: getTaskDescription
});

server.route({
    method: 'POST',
    path: pathURI,
    handler: createTask
});

server.route({
    method: 'PUT',
    path: pathURI + '/{id}',
    handler: updateTask
});

server.route({
    method: 'DELETE',
    path: pathURI + '/{id}',
    handler: deleteTask
});

/*
 * Méthodes de prise en charge des requêtes REST
*/
function getTasks(request, reply) {
    var type = acceptedType(request, reply);

    if ( type) {
	var data = tasks.find({});
	
	switch ( type ) {
	case 'json':
	    reply(data);
	    break;
	case 'xml':
	    var options = {
		arrayMap: {
		    tasks: "task"
		}
	    };
	    reply(js2xmlparser("tasks", data, options))
		.header('Content-type', 'application/xml');
	    break;
	}
    }
}

function getOneTask(request, reply) {
    var type = acceptedType(request, reply);
    if ( type) {
	var task = tasks.get(request.params.id);
	
	if ( task ) {
	    switch ( type ) {
	    case 'json':
		reply(task);
		break;
	    case 'xml':
		reply(js2xmlparser("task", task))
		    .header('Content-type', 'application/xml');
		break;
	    }
	}
	else {
	    reply('identifiant inconnu').code(404);
	}
    }
}

function getTaskName(request, reply) {
    var task = tasks.get(request.params.id);

    if ( task ) {
	reply(task.nom);
    }
    else {
	reply('identifiant inconnu').code(404);
    }
}

function getTaskDescription(request, reply) {
      var task = tasks.get(request.params.id);

    if ( task ) {
	reply(task.description);
    }
    else {
	reply('identifiant inconnu').code(404);
    }
}
  
function createTask(request, reply) {
    if ( request.payload && request.payload.nom ) {
	task = tasks.insert({ 'nom': request.payload.nom,
			      'description': request.payload.description || ''});
	reply().code(201).header('Location', request.path + '/' + task.$loki);
    }
    else {
	reply('nom manquant').code(400);
    }
}

function updateTask(request, reply) {
    task = tasks.get(request.params.id);
    if ( task ) {
	if ( request.payload ) {
	    task.nom = request.payload.nom || task.nom;
	    task.description = request.payload.description || task.description;
	    tasks.update(task);
	    reply().code(204);
	}
	else {
	    reply('pas de paramètres').code(400);
	}
    }
    else {
	reply('identifiant inconnu').code(404);
    }
}

function deleteTask(request, reply) {
    task = tasks.get(request.params.id);
    if ( task ) {
	tasks.remove(task);
	reply().code(204);
    }
    else {
	reply('identifiant inconnu').code(404);
    }
}

/*
 * Fonctions utilitaires
 */

// Utilisé pour prendre en charge les formats xml et json
function acceptedType(request, reply) {
    var accept = accepts(request);
    var type = accept.type(['json', 'xml']);
    
    if ( ! type ) {
	reply('Type MIME non supporté').code(406);
	return null;
    }

    return type;
}

// Fonction de chargement de la base de données
function loadHandler() {
    tasks = db.getCollection('tasks');
    if ( tasks === null ) {
	tasks = db.addCollection('tasks');
    }
}

// Fonction de mise en route du serveur REST
server.start( function() {
    console.log('To do démarré sur', server.info.uri);
});
