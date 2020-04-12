import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITelefone } from 'app/shared/model/telefone.model';

type EntityResponseType = HttpResponse<ITelefone>;
type EntityArrayResponseType = HttpResponse<ITelefone[]>;

@Injectable({ providedIn: 'root' })
export class TelefoneService {
  public resourceUrl = SERVER_API_URL + 'api/telefones';

  constructor(protected http: HttpClient) {}

  create(telefone: ITelefone): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(telefone);
    return this.http
      .post<ITelefone>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(telefone: ITelefone): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(telefone);
    return this.http
      .put<ITelefone>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITelefone>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITelefone[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(telefone: ITelefone): ITelefone {
    const copy: ITelefone = Object.assign({}, telefone, {
      dataCadastro: telefone.dataCadastro && telefone.dataCadastro.isValid() ? telefone.dataCadastro.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataCadastro = res.body.dataCadastro ? moment(res.body.dataCadastro) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((telefone: ITelefone) => {
        telefone.dataCadastro = telefone.dataCadastro ? moment(telefone.dataCadastro) : undefined;
      });
    }
    return res;
  }
}
