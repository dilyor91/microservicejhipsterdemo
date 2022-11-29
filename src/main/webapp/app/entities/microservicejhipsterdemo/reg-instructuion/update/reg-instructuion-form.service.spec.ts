import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../reg-instructuion.test-samples';

import { RegInstructuionFormService } from './reg-instructuion-form.service';

describe('RegInstructuion Form Service', () => {
  let service: RegInstructuionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RegInstructuionFormService);
  });

  describe('Service methods', () => {
    describe('createRegInstructuionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRegInstructuionFormGroup();

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

      it('passing IRegInstructuion should create a new form with FormGroup', () => {
        const formGroup = service.createRegInstructuionFormGroup(sampleWithRequiredData);

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

    describe('getRegInstructuion', () => {
      it('should return NewRegInstructuion for default RegInstructuion initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createRegInstructuionFormGroup(sampleWithNewData);

        const regInstructuion = service.getRegInstructuion(formGroup) as any;

        expect(regInstructuion).toMatchObject(sampleWithNewData);
      });

      it('should return NewRegInstructuion for empty RegInstructuion initial value', () => {
        const formGroup = service.createRegInstructuionFormGroup();

        const regInstructuion = service.getRegInstructuion(formGroup) as any;

        expect(regInstructuion).toMatchObject({});
      });

      it('should return IRegInstructuion', () => {
        const formGroup = service.createRegInstructuionFormGroup(sampleWithRequiredData);

        const regInstructuion = service.getRegInstructuion(formGroup) as any;

        expect(regInstructuion).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRegInstructuion should not enable id FormControl', () => {
        const formGroup = service.createRegInstructuionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRegInstructuion should disable id FormControl', () => {
        const formGroup = service.createRegInstructuionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
