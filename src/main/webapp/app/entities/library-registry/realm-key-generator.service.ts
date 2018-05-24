import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


@Injectable()
export class RealmKeyGeneratorService {

    private resourceUrl = 'http://localhost:8087/api/realmkey';

    constructor(private http: HttpClient) {
    }


    get() {
        console.log("request");
        const authToken = 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyNzIyNzMzM30.RDzUO6b00uxV4ozAim3Th8NxfkC78WZi-XB6qezr6HCTSCp7XRf1h9ZgCnJLMwBNiDfQbfp0WFg7xrUc3WbLxA';

        return this.http.get(this.resourceUrl, {
            responseType: 'text' as 'text',
            headers: new HttpHeaders({
                'Authorization': authToken
            })
        });
    }

}
