import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TelefoneService } from 'app/entities/telefone/telefone.service';
import { ITelefone, Telefone } from 'app/shared/model/telefone.model';
import { TipoTelefone } from 'app/shared/model/enumerations/tipo-telefone.model';

describe('Service Tests', () => {
  describe('Telefone Service', () => {
    let injector: TestBed;
    let service: TelefoneService;
    let httpMock: HttpTestingController;
    let elemDefault: ITelefone;
    let expectedResult: ITelefone | ITelefone[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TelefoneService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Telefone(0, 'AAAAAAA', 'AAAAAAA', TipoTelefone.CELULAR, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataCadastro: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Telefone', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataCadastro: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataCadastro: currentDate
          },
          returnedFromService
        );

        service.create(new Telefone()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Telefone', () => {
        const returnedFromService = Object.assign(
          {
            dDD: 'BBBBBB',
            numero: 'BBBBBB',
            tipo: 'BBBBBB',
            dataCadastro: currentDate.format(DATE_TIME_FORMAT),
            loginOperador: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataCadastro: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Telefone', () => {
        const returnedFromService = Object.assign(
          {
            dDD: 'BBBBBB',
            numero: 'BBBBBB',
            tipo: 'BBBBBB',
            dataCadastro: currentDate.format(DATE_TIME_FORMAT),
            loginOperador: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataCadastro: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Telefone', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
