import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {REALM_KEY_SERVER_API_URL} from "../../app.constants";


@Injectable()
export class RealmKeyGeneratorService {

    constructor(private http: HttpClient) {
    }


    getRealmkey() {
        return this.http.get(REALM_KEY_SERVER_API_URL, {
            responseType: 'text' as 'text'
        });
    }

}
