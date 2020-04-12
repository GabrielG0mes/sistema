import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'user-management',
        loadChildren: () => import('./user-management/user-management.module').then(m => m.UserManagementModule),
        data: {
          pageTitle: 'Operadores'
        }
      }
    ])
  ]
})
export class AdminRoutingModule {}
