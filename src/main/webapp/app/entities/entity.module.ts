import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'pessoa',
        loadChildren: () => import('./pessoa/pessoa.module').then(m => m.SistemaPessoaModule)
      },
      {
        path: 'telefone',
        loadChildren: () => import('./telefone/telefone.module').then(m => m.SistemaTelefoneModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class SistemaEntityModule {}
