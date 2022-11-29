import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRegInstructuion } from '../reg-instructuion.model';
import { RegInstructuionService } from '../service/reg-instructuion.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './reg-instructuion-delete-dialog.component.html',
})
export class RegInstructuionDeleteDialogComponent {
  regInstructuion?: IRegInstructuion;

  constructor(protected regInstructuionService: RegInstructuionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.regInstructuionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
