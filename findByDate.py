
import pandas as pd
import numpy as np
import random
import pickle
import os
import sys
from catboost import CatBoostClassifier, Pool

start = sys.argv[1]
goal = sys.argv[2]
start_ymd = sys.argv[3]
end_ymd = sys.argv[4]
start_flight = sys.argv[5]
end_flight = sys.argv[6]
c = os.getcwd()

os.chdir('C:\\Users\\smhrd08\\Desktop\\processbuilder')

cat_model = pickle.load(open('cat_model.pkl','rb'))
last_df = pd.read_csv('last_df.csv',encoding='euc-kr')


X = last_df
X = X.drop('Unnamed: 0',axis=1)
X = X.drop(['status2'],axis=1)


# 출발일에 대한 결과
select = X[(((X['airport']==start[:2]) & (X['goal'] == goal[:2])) & ((X['date']==start_ymd) & (X['airline']==start_flight)))].head(1)
print("r1" , cat_model.predict(select.drop('date',axis=1))[0][0],'r2',round(cat_model.predict_proba(select.drop('date',axis=1)).max(),3)*100,'r3')


# 도착일에 대한 결과
select = X[(((X['airport']==start[:2]) & (X['goal'] == goal[:2])) & ((X['date']==end_ymd) & (X['airline']==end_flight)))].head(1)
print('r4',cat_model.predict(select.drop('date',axis=1))[0][0],'r5',round(cat_model.predict_proba(select.drop('date',axis=1)).max(),3)*100,'r6')