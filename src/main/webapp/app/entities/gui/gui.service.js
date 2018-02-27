(function() {
    'use strict';
    angular
        .module('automationServiceHubApp')
        .factory('Gui', Gui);

    Gui.$inject = ['$resource'];

    function Gui ($resource) {
        var resourceUrl =  'api/guis/:id';

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
            },
            'update': { method:'PUT' }
        });
    }
})();
