import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SistemaTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { UserManagementDeleteDialogComponent } from 'app/admin/operador-management/operador-management-delete-dialog.component';
import { UserService } from 'app/core/operador/operador.service';

describe('Component Tests', () => {
  describe('User Management Delete Component', () => {
    let comp: UserManagementDeleteDialogComponent;
    let fixture: ComponentFixture<UserManagementDeleteDialogComponent>;
    let service: UserService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(async(() => {
      TestBed.configureTestingModule({
        imports: [SistemaTestModule],
        declarations: [UserManagementDeleteDialogComponent]
      })
        .overrideTemplate(UserManagementDeleteDialogComponent, '')
        .compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(UserManagementDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete('operador');
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith('operador');
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
