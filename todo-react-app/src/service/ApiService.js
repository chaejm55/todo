import { API_BASE_URL } from "../app-config";


export function call(api, method, request) {
    let options = {
        headers: new Headers({
            "Content-Type": "application/json"
        }),
        url: API_BASE_URL + api,
        method: method
    };
    if (request) {
        // GET METHOD
        options.body = JSON.stringify(request);
    }
    return fetch(options.url, options).then((response) =>
        response.json().then((json) => {
            if (!response.ok) {
                // 에러 응답이므로 reject return
                return Promise.reject(json);
            }
            return json;
        })
    );
}