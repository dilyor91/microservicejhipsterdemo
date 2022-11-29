import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAddmissionRules } from '../addmission-rules.model';
import { AddmissionRulesService } from '../service/addmission-rules.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './addmission-rules-delete-dialog.component.html',
})
export class AddmissionRulesDeleteDialogComponent {
  addmissionRules?: IAddmissionRules;

  constructor(protected addmissionRulesService: AddmissionRulesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.addmissionRulesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
