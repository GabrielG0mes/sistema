import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SistemaSharedModule } from 'app/shared/shared.module';
import { UserManagementComponent } from './user-management.component';
import { UserManagementDetailComponent } from './user-management-detail.component';
import { UserManagementUpdateComponent } from './user-management-update.component';
import { UserManagementDeleteDialogComponent } from './user-management-delete-dialog.component';
import { userManagementRoute } from './user-management.route';
import { UserManagementMostrarSenhaComponent } from './user-management-mostrar-senha.component';

@NgModule({
  imports: [SistemaSharedModule, RouterModule.forChild(userManagementRoute)],
  declarations: [
    UserManagementComponent,
    UserManagementDetailComponent,
    UserManagementUpdateComponent,
    UserManagementDeleteDialogComponent,
    UserManagementMostrarSenhaComponent
  ],
  entryComponents: [UserManagementDeleteDialogComponent, UserManagementMostrarSenhaComponent]
})
export class UserManagementModule {}
