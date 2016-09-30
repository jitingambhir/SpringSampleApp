(function() {
    'use strict';

    angular
        .module('chatAnalysisApp')
        .factory('LogsService', LogsService);

    LogsService.$inject = ['$resource'];

    function LogsService ($resource) {
        var service = $resource('management/jhipster/logs', {}, {
            'findAll': { method: 'GET', isArray: true},
            'changeLevel': { method: 'PUT'}
        });

        return service;
    }
})();
