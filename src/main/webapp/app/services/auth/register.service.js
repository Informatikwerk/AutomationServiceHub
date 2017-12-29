(function () {
    'use strict';

    angular
        .module('automationServiceHubApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
