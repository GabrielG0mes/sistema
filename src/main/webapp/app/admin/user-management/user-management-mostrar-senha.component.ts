import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { UserService } from '../../core/user/user.service';
import { User } from '../../core/user/user.model';

@Component({
  selector: 'jhi-user-mgmt-mostrar-senha',
  templateUrl: './user-management-mostrar-senha.component.html'
})
export class UserManagementMostrarSenhaComponent {
  user?: User;

  constructor(private userService: UserService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  ok(): void {
    this.activeModal.dismiss();
  }
}
