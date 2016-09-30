(function () {
    'use strict';

    angular
        .module('chatAnalysisApp')
        .factory('TrendingTopics', User);

    User.$inject = ['$resource'];

    function User ($resource) {
        var service = $resource('api/topics/trending/', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });

        return service;
    }
})();
