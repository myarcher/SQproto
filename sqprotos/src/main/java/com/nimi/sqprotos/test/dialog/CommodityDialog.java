package com.nimi.sqprotos.test.dialog;


/**
 * Created by jyjin on 2017/5/20.
 */

public class CommodityDialog {
    /*TextView shopping_item_item2_remove;
    TextView shopping_item_item2_count;
    TextView shopping_item_item2_add;
    ImageView shopping_select_head;
    TextView shopping_select_title;
    TextView shopping_select_price;
    TextView shopping_select_count;
    TextView shopping_select_bnt1;
    TextView shopping_select_bnt2;
    RecyclerView shopping_select_lv;
    ImageView shopping_select_canel;
    private SActivity activitys;
    private Dialog dialog;
    private CallBackListener listener;
    //private List<ShareItem> shareItemList;
    private Display display;
    private Map<String, Object> beans;
    private View view;
    private List<Map<String, Object>> mList, value1, value2;
    private List<String> value3;
    Map<String, Object> data1, data2, data3;
    private List<Tag> list1, list2;
    private int po1, po2;
    private String sele;
    int count = 1;
    private String stokes = "0";
private int sw=0;
    public CommodityDialog(SActivity activitys, Map<String, Object> beans, CallBackListener listener) {
        this.activitys = activitys;
        this.listener = listener;
        this.beans = beans;

        WindowManager windowManager = (WindowManager) activitys.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public CommodityDialog builder() {
        view = LayoutInflater.from(activitys).inflate(R.layout.commodity_dialog, null);
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


    public CommodityDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }


    public void dismiss() {
        dialog.dismiss();
    }

    public CommodityDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void show() {
        dialog.show();
    }


    private void initview() {
        sw= (int) activitys.getResources().getDimension(R.dimen.dimen_100px);
        shopping_select_head = (ImageView) view.findViewById(R.id.shopping_select_head);
        shopping_select_title = (TextView) view.findViewById(R.id.shopping_select_title);
        shopping_select_price = (TextView) view.findViewById(R.id.shopping_select_price);
        shopping_select_count = (TextView) view.findViewById(R.id.shopping_select_count);
        shopping_select_bnt1 = (TextView) view.findViewById(R.id.shopping_select_bnt1);
        shopping_select_bnt2 = (TextView) view.findViewById(R.id.shopping_select_bnt2);
        shopping_select_lv = (RecyclerView) view.findViewById(R.id.shopping_select_lv);
        shopping_select_canel = (ImageView) view.findViewById(R.id.shopping_select_canel);
        shopping_item_item2_remove = (TextView) view.findViewById(R.id.shopping_item_item2_remove);
        shopping_item_item2_count = (TextView) view.findViewById(R.id.shopping_item_item2_count);
        shopping_item_item2_add = (TextView) view.findViewById(R.id.shopping_item_item2_add);
        if (beans != null) {
            Map<String, Object> goods = (Map<String, Object>) beans.get("goods");
            if (goods != null) {
                setImgs(goods.get("thumb_img") +"");
                shopping_select_title.setText(goods.get("title") + "");
                shopping_select_price.setText("￥" + goods.get("price"));
                shopping_select_count.setText("库存" + goods.get("stoke") + "件");
                stokes = goods.get("stoke") + "";
            }
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
            shopping_select_bnt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.callBack(4, count, sele, null);
                }
            });
            shopping_select_bnt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.callBack(3, count, sele, null);
                }
            });
            shopping_item_item2_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count--;
                    if (count < 1) {
                        count = 1;
                        activitys.showMess("数量不能小于1", -1, MyToast.Types.NOTI, null);
                    }
                    shopping_item_item2_count.setText(count + "");

                }
            });
            shopping_item_item2_count.setText(count + "");
            shopping_item_item2_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;
                    int s = Integer.parseInt(stokes);
                    if (count > s) {
                        count = count - 1;
                        activitys.showMess("数量不能大于库存", -1, MyToast.Types.NOTI, null);
                    }
                    shopping_item_item2_count.setText(count + "");
                }
            });
        }
    }

private void setImgs(String url){
    Units.loadImage(activitys, url+ "",sw,sw, shopping_select_head, R.mipmap.default_img4);

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
            Map<String, Object> item1 = new HashMap<>();
            Map<String, Object> item2 = new HashMap<>();
            if (data3 != null) {
                list1 = new ArrayList<>();
                String names1=data3.get("name")+"";
                if(TextUtils.isEmpty(names1)||names1.equals("null")){
                    names1="属性";
                }
                item1.put("name",names1);
                if (value3 != null) {
                    for (String ma : value3) {
                        if (!TextUtils.isEmpty(ma) && !ma.equals("null")) {
                            list1.add(new Tag(Tag.TYPE_TEXT, 0, ma));
                        }
                    }
                }
            }
            if (data2 != null) {
                list2 = new ArrayList<>();
                String names2=data2.get("name")+"";
                if(TextUtils.isEmpty(names2)||names2.equals("null")){
                    names2="颜色";
                }
                item2.put("name",names2);
                if (value2 != null) {
                    for (Map<String, Object> ma1 : value2) {
                        String color_name = ma1.get("name") + "";
                        if (!TextUtils.isEmpty(color_name) && !color_name.equals("null")) {
                            list2.add(new Tag(Tag.TYPE_TEXT, Integer.parseInt(ma1.get("id") + ""), color_name));
                        }
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
            
            String[] strings = getStokeAndPrice(c, a);
            shopping_select_count.setText("库存" + strings[0] + "件");
            shopping_select_price.setText("￥" + strings[1]);
            setImgs(strings[2]  +"");
            stokes = strings[0] + "";
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
        String[] s = getStokeAndPrice(c, a);
        shopping_select_count.setText("库存" + s[0] + "件");
        shopping_select_price.setText("￥" + s[1]);
        stokes = s[0] + "";
        setImgs(s[2]  +"");
    }

    private String[] getStokeAndPrice(String colorid, String attrid) {
        String stoke = "0";
        String price = "0";
        String color_img="";
        String[] pr_st = new String[3];
        if (value1 != null && value1.size() > 0) {
            for (Map<String, Object> ma : value1) {
                String sizeattr_id = ma.get("sizeattr_id") + "";
                String attribute = ma.get("attribute") + "";
                String color = ma.get("color_id") + "";
                if (colorid.equals(color) && attribute.equals(attrid)) {
                    stoke = ma.get("stock") + "";
                    price = ma.get("price") + "";
                    color_img=ma.get("color_img")+"";
                    sele = sizeattr_id;
                    break;
                } else if ((colorid.equals("") || colorid.equals("null")) && attribute.equals(attrid)) {
                    stoke = ma.get("stock") + "";
                    price = ma.get("price") + "";
                    sele = sizeattr_id;
                    color_img=ma.get("color_img")+"";
                    break;
                }else  if((attrid.equals("") || attrid.equals("null")) && color.equals(colorid)){
                    stoke = ma.get("stock") + "";
                    price = ma.get("price") + "";
                    color_img=ma.get("color_img")+"";
                    sele = sizeattr_id;
                    break;
                }
            }
        } else {
            if (beans != null) {
                Map<String, Object> goods = (Map<String, Object>) beans.get("goods");
                stoke = goods.get("repertory") + "";
                price = goods.get("price") + "";
                color_img=goods.get("thumb_img")+"";
            }
        }
        pr_st[0] = stoke;
        pr_st[1] = price;
        pr_st[2]=color_img;
        return pr_st;
    }*/
}
