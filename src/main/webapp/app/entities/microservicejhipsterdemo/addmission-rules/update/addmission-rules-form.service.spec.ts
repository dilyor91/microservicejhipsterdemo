import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../addmission-rules.test-samples';

import { AddmissionRulesFormService } from './addmission-rules-form.service';

describe('AddmissionRules Form Service', () => {
  let service: AddmissionRulesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddmissionRulesFormService);
  });

  describe('Service methods', () => {
    describe('createAddmissionRulesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAddmissionRulesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titleUz: expect.any(Object),
            titleRu: expect.any(Object),
            titleKr: expect.any(Object),
            contentUz: expect.any(Object),
            contentRu: expect.any(Object),
            contentKr: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });

      it('passing IAddmissionRules should create a new form with FormGroup', () => {
        const formGroup = service.createAddmissionRulesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titleUz: expect.any(Object),
            titleRu: expect.any(Object),
            titleKr: expect.any(Object),
            contentUz: expect.any(Object),
            contentRu: expect.any(Object),
            contentKr: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });
    });

    describe('getAddmissionRules', () => {
      it('should return NewAddmissionRules for default AddmissionRules initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAddmissionRulesFormGroup(sampleWithNewData);

        const addmissionRules = service.getAddmissionRules(formGroup) as any;

        expect(addmissionRules).toMatchObject(sampleWithNewData);
      });

      it('should return NewAddmissionRules for empty AddmissionRules initial value', () => {
        const formGroup = service.createAddmissionRulesFormGroup();

        const addmissionRules = service.getAddmissionRules(formGroup) as any;

        expect(addmissionRules).toMatchObject({});
      });

      it('should return IAddmissionRules', () => {
        const formGroup = service.createAddmissionRulesFormGroup(sampleWithRequiredData);

        const addmissionRules = service.getAddmissionRules(formGroup) as any;

        expect(addmissionRules).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAddmissionRules should not enable id FormControl', () => {
        const formGroup = service.createAddmissionRulesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAddmissionRules should disable id FormControl', () => {
        const formGroup = service.createAddmissionRulesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
