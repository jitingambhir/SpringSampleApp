(function() {
    'use strict';
    angular
        .module('chatAnalysisApp')
        .factory('ChatSessionTrending', ChatSessionTrending);

    ChatSessionTrending.$inject = ['$resource', 'DateUtils'];

    function ChatSessionTrending ($resource, DateUtils) {
        var resourceUrl =  'api/chat-sessions/trending';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            }
        });
    }
})();
