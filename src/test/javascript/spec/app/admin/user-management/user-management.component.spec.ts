import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SistemaTestModule } from '../../../test.module';
import { UserManagementComponent } from 'app/admin/operador-management/operador-management.component';
import { UserService } from 'app/core/operador/operador.service';
import { User } from 'app/core/operador/operador.model';

describe('Component Tests', () => {
  describe('User Management Component', () => {
    let comp: UserManagementComponent;
    let fixture: ComponentFixture<UserManagementComponent>;
    let service: UserService;

    beforeEach(async(() => {
      TestBed.configureTestingModule({
        imports: [SistemaTestModule],
        declarations: [UserManagementComponent]
      })
        .overrideTemplate(UserManagementComponent, '')
        .compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(UserManagementComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserService);
    });

    describe('OnInit', () => {
      it('Should call load all on init', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          const headers = new HttpHeaders().append('link', 'link;link');
          spyOn(service, 'query').and.returnValue(
            of(
              new HttpResponse({
                body: [new User(123)],
                headers
              })
            )
          );

          // WHEN
          comp.ngOnInit();
          tick(); // simulate async

          // THEN
          expect(service.query).toHaveBeenCalled();
          expect(comp.operadors && comp.operadors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        })
      ));
    });

    describe('setActive', () => {
      it('Should update operador and call load all', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          const headers = new HttpHeaders().append('link', 'link;link');
          const operador = new User(123);
          spyOn(service, 'query').and.returnValue(
            of(
              new HttpResponse({
                body: [operador],
                headers
              })
            )
          );
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ status: 200 })));

          // WHEN
          comp.setActive(operador, true);
          tick(); // simulate async

          // THEN
          expect(service.update).toHaveBeenCalledWith({ ...operador, activated: true });
          expect(service.query).toHaveBeenCalled();
          expect(comp.operadors && comp.operadors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        })
      ));
    });
  });
});
