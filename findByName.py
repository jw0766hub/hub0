#!/usr/bin/env python
# coding: utf-8

# In[192]:



import pandas as pd
import numpy as np
import random
import pickle
import os
import sys
from catboost import CatBoostClassifier, Pool

fn = sys.argv[1]
print(fn)
c = os.getcwd()

os.chdir('C:\\Users\\smhrd08\\Desktop\\processbuilder')

cat_model = pickle.load(open('cat_model.pkl','rb'))
last_df = pd.read_csv('last_dff.csv',encoding='euc-kr')

X = last_df


X = X.drop(['status2'],axis=1)


X = X.drop('Unnamed: 0',axis=1)


X =X.dropna()



X['fn'].tail(1)



select = X[X['fn']==fn].tail(1)


# In[198]:


# 출발일에 대한 결과
print("r1" , cat_model.predict(select.drop(['date','fn'],axis=1))[0][0],'r2',round(cat_model.predict_proba(select.drop(['date','fn'],axis=1)).max(),3)*100,'r3')
print("r4",select["airline"].values[0],"r5",select["airport"].values[0],"r6",select["goal"].values[0],"r7",select["date"].values[0],"r8")

# In[ ]:




