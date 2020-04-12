import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { User } from '../../core/user/user.model';
import { UserService } from '../../core/user/user.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserManagementMostrarSenhaComponent } from './user-management-mostrar-senha.component';

@Component({
  selector: 'jhi-user-mgmt-update',
  templateUrl: './user-management-update.component.html'
})
export class UserManagementUpdateComponent implements OnInit {
  user!: User;
  perfis: string[] = [];
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    login: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(15), Validators.pattern('^[_@A-Za-z-]*')]],
    nome: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(100), Validators.pattern('^[A-Za-z- ]*')]],
    perfis: []
  });

  constructor(private userService: UserService, private route: ActivatedRoute, private modalService: NgbModal, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ user }) => {
      if (user) {
        this.user = user;
        this.updateForm(user);
      }
    });
    this.userService.authorities().subscribe(authorities => {
      this.perfis = authorities;
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    this.updateUser(this.user);
    if (this.user.id !== undefined) {
      this.userService.update(this.user).subscribe(
        () => this.onSaveSuccess(),
        () => this.onSaveError()
      );
    } else {
      this.userService.create(this.user).subscribe(
        data => {
          this.mostrarSenha(data);
          this.onSaveSuccess();
        },
        () => this.onSaveError()
      );
    }
  }

  mostrarSenha(user: User): void {
    const modalRef = this.modalService.open(UserManagementMostrarSenhaComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = user;
  }

  private updateForm(user: User): void {
    this.editForm.patchValue({
      id: user.id,
      login: user.login,
      nome: user.nome,
      perfis: user.perfis
    });
  }

  private updateUser(user: User): void {
    user.login = this.editForm.get(['login'])!.value;
    user.nome = this.editForm.get(['nome'])!.value;
    user.perfis = this.editForm.get(['perfis'])!.value;
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }
}
