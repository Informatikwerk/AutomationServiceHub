import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';


@Injectable()
export class RealmKeyGeneratorService {

    private resourceUrl = 'http://localhost:8087/api/realmkey';

    constructor(private http: HttpClient) {
    }


    get() {
        console.log("request");
        const authToken = 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyNjk5MjIzNX0.CY2sJ3yuRTRQumvPH9-u0Gn8Ba0Up0a4YT0KbznRUEOtE1Z_ze74gfpy_zbxbg7IqiI_1qVfxe0qzVk1HnV9Jg';

        return this.http.get(this.resourceUrl, {
            responseType: 'text' as 'text',
            headers: new HttpHeaders({
                'Authorization': authToken
            })
        });
    }

}
