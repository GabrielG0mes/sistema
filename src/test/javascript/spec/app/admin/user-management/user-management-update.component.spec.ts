import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Authority } from 'app/shared/constants/perfil.constants';
import { SistemaTestModule } from '../../../test.module';
import { UserManagementUpdateComponent } from 'app/admin/operador-management/operador-management-update.component';
import { UserService } from 'app/core/operador/operador.service';
import { User } from 'app/core/operador/operador.model';

describe('Component Tests', () => {
  describe('User Management Update Component', () => {
    let comp: UserManagementUpdateComponent;
    let fixture: ComponentFixture<UserManagementUpdateComponent>;
    let service: UserService;
    const route: ActivatedRoute = ({
      data: of({ operador: new User(1, 'operador', 'first', 'last', 'first@last.com', true, 'en', [Authority.USER], 'admin') })
    } as any) as ActivatedRoute;

    beforeEach(async(() => {
      TestBed.configureTestingModule({
        imports: [SistemaTestModule],
        declarations: [UserManagementUpdateComponent],
        providers: [
          FormBuilder,
          {
            provide: ActivatedRoute,
            useValue: route
          }
        ]
      })
        .overrideTemplate(UserManagementUpdateComponent, '')
        .compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(UserManagementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserService);
    });

    describe('OnInit', () => {
      it('Should load authorities and language on init', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'authorities').and.returnValue(of(['USER']));

          // WHEN
          comp.ngOnInit();

          // THEN
          expect(service.authorities).toHaveBeenCalled();
          expect(comp.authorities).toEqual(['USER']);
        })
      ));
    });

    describe('save', () => {
      it('Should call update service on save for existing operador', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          const entity = new User(123);
          spyOn(service, 'update').and.returnValue(
            of(
              new HttpResponse({
                body: entity
              })
            )
          );
          comp.operador = entity;
          comp.editForm.patchValue({ id: entity.id });
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.update).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      ));

      it('Should call create service on save for new operador', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          const entity = new User();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.operador = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.create).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      ));
    });
  });
});
