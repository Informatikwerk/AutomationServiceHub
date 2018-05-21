import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';


@Injectable()
export class RealmKeyGeneratorService {

    private resourceUrl = 'http://localhost:8089/realmkeygenerator/api/realmkey';

    constructor(private http: HttpClient) {
    }


    get(): Observable<HttpResponse<string>> {
        const authToken = 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyNjk5MjIzNX0.CY2sJ3yuRTRQumvPH9-u0Gn8Ba0Up0a4YT0KbznRUEOtE1Z_ze74gfpy_zbxbg7IqiI_1qVfxe0qzVk1HnV9Jg';

        return this.http.get<string>(this.resourceUrl, {
            observe: 'response', headers: new HttpHeaders({
                'Authorization': authToken,
                observe: 'response'
            })
        });
    }

}
