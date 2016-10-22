/**
 * Created by lipeng on 16/10/22.
 */

(function () {
    function Bridge(root, service) {
        this.root = root;
        this.service = service;
        this.jsonString = false;
        this.useJsonString = function () {
            this.jsonString = true;
            return this;
        }
        this.post = function (method, parameter, success) {
            $.post(root, {
                'service': service,
                'method': method,
                'param': this.jsonString ? parameter : JSON.stringify(parameter),
                'paramType': 'json'
            }, success);
        }
    }

    window.Bridge = Bridge
})()