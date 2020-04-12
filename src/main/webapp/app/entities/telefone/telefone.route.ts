import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITelefone, Telefone } from 'app/shared/model/telefone.model';
import { TelefoneService } from './telefone.service';
import { TelefoneComponent } from './telefone.component';
import { TelefoneDetailComponent } from './telefone-detail.component';
import { TelefoneUpdateComponent } from './telefone-update.component';

@Injectable({ providedIn: 'root' })
export class TelefoneResolve implements Resolve<ITelefone> {
  constructor(private service: TelefoneService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITelefone> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((telefone: HttpResponse<Telefone>) => {
          if (telefone.body) {
            return of(telefone.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Telefone());
  }
}

export const telefoneRoute: Routes = [
  {
    path: '',
    component: TelefoneComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.GERENTE, Authority.ANALISTA],
      defaultSort: 'id,asc',
      pageTitle: 'Telefones'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TelefoneDetailComponent,
    resolve: {
      telefone: TelefoneResolve
    },
    data: {
      authorities: [Authority.GERENTE, Authority.ANALISTA],
      pageTitle: 'Telefones'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TelefoneUpdateComponent,
    resolve: {
      telefone: TelefoneResolve
    },
    data: {
      authorities: [Authority.GERENTE],
      pageTitle: 'Telefones'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TelefoneUpdateComponent,
    resolve: {
      telefone: TelefoneResolve
    },
    data: {
      authorities: [Authority.GERENTE],
      pageTitle: 'Telefones'
    },
    canActivate: [UserRouteAccessService]
  }
];
