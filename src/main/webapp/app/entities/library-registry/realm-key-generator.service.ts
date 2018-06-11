import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


@Injectable()
export class RealmKeyGeneratorService {

    private resourceUrl = 'http://localhost:8083/api/realmkey';

    constructor(private http: HttpClient) {
    }


    getRealmkey() {
        console.log("request");

        return this.http.get(this.resourceUrl, {
            responseType: 'text' as 'text'
        });
    }

}
