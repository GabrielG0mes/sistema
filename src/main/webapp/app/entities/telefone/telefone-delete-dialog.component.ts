import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITelefone } from 'app/shared/model/telefone.model';
import { TelefoneService } from './telefone.service';

@Component({
  templateUrl: './telefone-delete-dialog.component.html'
})
export class TelefoneDeleteDialogComponent {
  telefone?: ITelefone;

  constructor(protected telefoneService: TelefoneService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.telefoneService.delete(id).subscribe(() => {
      this.eventManager.broadcast('telefoneListModification');
      this.activeModal.close();
    });
  }
}
