import { TestBed } from '@angular/core/testing';
import { HttpErrorResponse } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { JhiDateUtils } from 'ng-jhipster';

import { Authority } from 'app/shared/constants/perfil.constants';
import { UserService } from 'app/core/operador/operador.service';
import { User } from 'app/core/operador/operador.model';
import { SERVER_API_URL } from 'app/app.constants';

describe('Service Tests', () => {
  describe('User Service', () => {
    let service: UserService;
    let httpMock: HttpTestingController;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [JhiDateUtils]
      });

      service = TestBed.get(UserService);
      httpMock = TestBed.get(HttpTestingController);
    });

    afterEach(() => {
      httpMock.verify();
    });

    describe('Service methods', () => {
      it('should call correct URL', () => {
        service.find('operador').subscribe();

        const req = httpMock.expectOne({ method: 'GET' });
        const resourceUrl = SERVER_API_URL + 'api/operadors';
        expect(req.request.url).toEqual(`${resourceUrl}/operador`);
      });

      it('should return User', () => {
        let expectedResult: string | undefined;

        service.find('operador').subscribe(received => {
          expectedResult = received.login;
        });

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(new User(1, 'operador'));
        expect(expectedResult).toEqual('operador');
      });

      it('should return Authorities', () => {
        let expectedResult: string[] = [];

        service.authorities().subscribe(authorities => {
          expectedResult = authorities;
        });
        const req = httpMock.expectOne({ method: 'GET' });

        req.flush([Authority.USER, Authority.ADMIN]);
        expect(expectedResult).toEqual([Authority.USER, Authority.ADMIN]);
      });

      it('should propagate not found response', () => {
        let expectedResult = 0;

        service.find('operador').subscribe(null, (error: HttpErrorResponse) => {
          expectedResult = error.status;
        });

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush('Invalid request parameters', {
          status: 404,
          statusText: 'Bad Request'
        });
        expect(expectedResult).toEqual(404);
      });
    });
  });
});
