(function () {
    'use strict';

    angular
        .module('chatAnalysisApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
