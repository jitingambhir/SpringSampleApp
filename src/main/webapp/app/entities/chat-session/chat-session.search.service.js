(function() {
    'use strict';

    angular
        .module('chatAnalysisApp')
        .factory('ChatSessionSearch', ChatSessionSearch);

    ChatSessionSearch.$inject = ['$resource'];

    function ChatSessionSearch($resource) {
        var resourceUrl =  'api/_search/chat-sessions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
