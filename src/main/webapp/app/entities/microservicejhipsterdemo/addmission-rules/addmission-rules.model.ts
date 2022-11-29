export interface IAddmissionRules {
  id: number;
  titleUz?: string | null;
  titleRu?: string | null;
  titleKr?: string | null;
  contentUz?: string | null;
  contentRu?: string | null;
  contentKr?: string | null;
  status?: boolean | null;
}

export type NewAddmissionRules = Omit<IAddmissionRules, 'id'> & { id: null };
