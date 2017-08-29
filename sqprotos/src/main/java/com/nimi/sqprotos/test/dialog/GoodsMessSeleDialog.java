package com.nimi.sqprotos.test.dialog;



/**
 * Created by Administrator on 2016/11/10.
 */
public class GoodsMessSeleDialog {
  /*  ImageView shopping_select_head;
    TextView shopping_select_title;
    TextView shopping_select_price;
    TextView shopping_select_count;
    TextView shopping_select_bnt;
    RecyclerView shopping_select_lv;
    ImageView shopping_select_canel;
    private SActivity activitys;
    private Dialog dialog;
    private CallBackListener listener;
    //private List<ShareItem> shareItemList;
    private Display display;
    private Map<String, Object> beans;
    private View view;
    private int group_po;
    private List<Tag> list1, list2;
    private int po1, po2;
    private Map<String, Object> sele;
    private List<Map<String, Object>> be;
    private int pos;
    private List<Map<String, Object>> mList, value1, value2;
    private List<String> value3;
    Map<String, Object> data1, data2, data3;
    private int w = 0;

    public GoodsMessSeleDialog(SActivity activitys, Object beans, CallBackListener listener, int pos, int group_po) {
        this.activitys = activitys;
        this.listener = listener;
        this.group_po = group_po;
        this.pos = pos;
        be = (List<Map<String, Object>>) beans;
        if (be != null) {
            this.beans = be.get(pos);
            sele = this.beans;
        }

        WindowManager windowManager = (WindowManager) activitys.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public GoodsMessSeleDialog builder() {
        view = LayoutInflater.from(activitys).inflate(R.layout.shopping_select_linear, null);
        w= (int) activitys.getResources().getDimension(R.dimen.dimen_100px);
        initview();
        view.setMinimumWidth(display.getWidth());
        dialog = new Dialog(activitys, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        return this;
    }


    public GoodsMessSeleDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }


    public void dismiss() {
        dialog.dismiss();
    }

    public GoodsMessSeleDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void show() {
        dialog.show();
    }


    private void initview() {
        shopping_select_head = (ImageView) view.findViewById(R.id.shopping_select_head);
        shopping_select_title = (TextView) view.findViewById(R.id.shopping_select_title);
        shopping_select_price = (TextView) view.findViewById(R.id.shopping_select_price);
        shopping_select_count = (TextView) view.findViewById(R.id.shopping_select_count);
        shopping_select_bnt = (TextView) view.findViewById(R.id.shopping_select_bnt);
        shopping_select_lv = (RecyclerView) view.findViewById(R.id.shopping_select_lv);
        shopping_select_canel = (ImageView) view.findViewById(R.id.shopping_select_canel);
        if (beans != null) {
            setImg(beans.get("thumb_img")+"");
            shopping_select_title.setText(beans.get("title") + "");
            shopping_select_price.setText("￥" + beans.get("price"));
            shopping_select_count.setText("库存" + beans.get("stoke") + "件");
            shopping_select_canel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(activitys);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            shopping_select_lv.setLayoutManager(layoutManager);
            initData();
            shopping_select_lv.setAdapter(new CustomAdapter(activitys, mList, R.layout.shopping_select_item) {
                @Override
                public void bindItemHolder(ViHolder holder, final Map<String, Object> data, int position) {
                    holder.setText(R.id.shopping_select_size_name, data.get("name") + "");
                    TagGroup tagGroup = holder.getView(R.id.shopping_select_size_tags);
                    tagGroup.setTag((List<Tag>) data.get("goods"));
                    tagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                        @Override
                        public void onTagClick(TagView tagView, Tag tag) {
                            String type = data.get("type") + "";
                            if (type.equals("c")) {
                                po2 = tag.getPo();
                            } else {
                                po1 = tag.getPo();
                            }
                            setstike();
                        }
                    });
                }
            });
            shopping_select_bnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int st = -1;
                    if (!sele.equals(beans)) {
                        st = pos;
                        beans.put("sizeattr_id", sele.get("sizeattr_id"));
                        beans.put("attribute", sele.get("attribute"));
                        beans.put("color_id", sele.get("color_id"));
                        beans.put("color", sele.get("color"));
                        be.set(pos, beans);
                    }
                    listener.callBack(4, group_po, be, st);
                }
            });
        }
    }


    private void setImg(String url){
        Units.loadImage(activitys,  url+ "",w,w, shopping_select_head, R.mipmap.default_img4);
    }
    private void initData() {
        mList = new ArrayList<>();
        if (beans != null) {
            data1 = (Map<String, Object>) beans.get("attr_list");
            data2 = (Map<String, Object>) beans.get("color_list");
            data3 = (Map<String, Object>) beans.get("attribute_list");
            value1 = (List<Map<String, Object>>) data1.get("value");
            value2 = (List<Map<String, Object>>) data2.get("value");
            value3 = (List<String>) data3.get("value");
            if (data3 != null) {
                list1 = new ArrayList<>();
                Map<String, Object> item1 = new HashMap<>();
                String names1 = data3.get("name") + "";
                if (TextUtils.isEmpty(names1) || names1.equals("null")) {
                    names1 = "属性";
                }
                item1.put("name", names1);
                if (data3 != null) {
                    for (String ma : value3) {
                        if (!TextUtils.isEmpty(ma) && !ma.equals("null")) {
                            list1.add(new Tag(Tag.TYPE_TEXT, 0, ma));
                        }
                    }
                }

                if (data2 != null) {
                    list2 = new ArrayList<>();
                    Map<String, Object> item2 = new HashMap<>();
                    String names2 = data2.get("name") + "";
                    if (TextUtils.isEmpty(names2) || names2.equals("null")) {
                        names2 = "颜色";
                    }
                    item2.put("name", names2);
                    if (value2 != null) {
                        for (Map<String, Object> ma : value2) {
                            String color_name = ma.get("color_name") + "";
                            if (!TextUtils.isEmpty(color_name) && !color_name.equals("null")) {
                                list2.add(new Tag(Tag.TYPE_TEXT, Integer.parseInt(ma.get("color_id") + ""), color_name));
                            }
                        }
                    }

                    String c = "";
                    if (list2.size() > 0) {
                        c = list2.get(0).getIconID() + "";
                        item2.put("goods", list2);
                        item2.put("type", "c");
                        mList.add(item2);
                    }
                    String a = "";
                    if (list1.size() > 0) {
                        a = list1.get(0).getTagText() + "";
                        item1.put("goods", list1);
                        item1.put("type", "a");
                        mList.add(item1);
                    }
                    String s[] = getStoke(a, c);
                    shopping_select_count.setText("库存" + s[0] + "件");
                    shopping_select_price.setText("￥" + s[1]);
                    setImg( s[2] + "");
                }
            }
        }
    }

    private void setstike() {
        String a = "";
        String c = "";
        if (list1.size() > po1) {
            a = list1.get(po1).getTagText() + "";
        }
        if (list2.size() > po2) {
            c = list2.get(po2).getIconID() + "";
        }
        String[] s = getStoke(a, c);
        shopping_select_count.setText("库存" + s[0] + "件");
        shopping_select_price.setText("￥" + s[1]);
        setImg( s[2] + "");
    }

    private String[] getStoke(String attrid, String colorid) {
        String stoke = "0";
        String price = "0";
        String color_img = "";
        String[] pr_st = new String[3];
        if (value1 != null && value1.size() > 0) {
            for (Map<String, Object> ma : value1) {
                String sizeattr_id = ma.get("sizeattr_id") + "";
                String attribute = ma.get("attribute") + "";
                String color = ma.get("color_id") + "";
                if (colorid.equals(color) && attribute.equals(attrid)) {
                    stoke = ma.get("stock") + "";
                    price = ma.get("price") + "";
                    color_img = ma.get("color_img") + "";
                    sele = ma;
                    break;
                } else if ((colorid.equals("") || colorid.equals("null")) && attribute.equals(attrid)) {
                    stoke = ma.get("stock") + "";
                    color_img = ma.get("color_img") + "";
                    price = ma.get("price") + "";
                    sele = ma;
                    break;
                } else if ((attrid.equals("") || attrid.equals("null")) && color.equals(colorid)) {
                    stoke = ma.get("stock") + "";
                    price = ma.get("price") + "";
                    color_img = ma.get("color_img") + "";
                    sele = ma;
                    break;
                }
            }
        } else {
            stoke = beans.get("stoke") + "";
            price = beans.get("price") + "";
            color_img = beans.get("thumb_img") + "";
        }
        pr_st[0] = stoke;
        pr_st[1] = price;
        pr_st[2] = color_img;
        return pr_st;
    }*/
}
