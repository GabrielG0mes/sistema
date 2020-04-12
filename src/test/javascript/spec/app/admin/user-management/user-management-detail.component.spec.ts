import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Authority } from 'app/shared/constants/perfil.constants';
import { SistemaTestModule } from '../../../test.module';
import { UserManagementDetailComponent } from 'app/admin/operador-management/operador-management-detail.component';
import { User } from 'app/core/operador/operador.model';

describe('Component Tests', () => {
  describe('User Management Detail Component', () => {
    let comp: UserManagementDetailComponent;
    let fixture: ComponentFixture<UserManagementDetailComponent>;
    const route: ActivatedRoute = ({
      data: of({ operador: new User(1, 'operador', 'first', 'last', 'first@last.com', true, 'en', [Authority.USER], 'admin') })
    } as any) as ActivatedRoute;

    beforeEach(async(() => {
      TestBed.configureTestingModule({
        imports: [SistemaTestModule],
        declarations: [UserManagementDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: route
          }
        ]
      })
        .overrideTemplate(UserManagementDetailComponent, '')
        .compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(UserManagementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.operador).toEqual(
          jasmine.objectContaining({
            id: 1,
            login: 'operador',
            firstName: 'first',
            lastName: 'last',
            email: 'first@last.com',
            activated: true,
            langKey: 'en',
            authorities: [Authority.USER],
            createdBy: 'admin'
          })
        );
      });
    });
  });
});
