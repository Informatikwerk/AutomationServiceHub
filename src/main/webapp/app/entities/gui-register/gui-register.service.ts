import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { GuiRegister } from './gui-register.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<GuiRegister>;

@Injectable()
export class GuiRegisterService {

    private resourceUrl =  SERVER_API_URL + 'api/gui-registers';

    constructor(private http: HttpClient) { }

    create(guiRegister: GuiRegister): Observable<EntityResponseType> {
        const copy = this.convert(guiRegister);
        return this.http.post<GuiRegister>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(guiRegister: GuiRegister): Observable<EntityResponseType> {
        const copy = this.convert(guiRegister);
        return this.http.put<GuiRegister>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<GuiRegister>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<GuiRegister[]>> {
        const options = createRequestOption(req);
        return this.http.get<GuiRegister[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<GuiRegister[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: GuiRegister = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<GuiRegister[]>): HttpResponse<GuiRegister[]> {
        const jsonResponse: GuiRegister[] = res.body;
        const body: GuiRegister[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to GuiRegister.
     */
    private convertItemFromServer(guiRegister: GuiRegister): GuiRegister {
        const copy: GuiRegister = Object.assign({}, guiRegister);
        return copy;
    }

    /**
     * Convert a GuiRegister to a JSON which can be sent to the server.
     */
    private convert(guiRegister: GuiRegister): GuiRegister {
        const copy: GuiRegister = Object.assign({}, guiRegister);
        return copy;
    }
}
